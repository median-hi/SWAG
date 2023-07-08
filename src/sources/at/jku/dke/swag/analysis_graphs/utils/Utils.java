package at.jku.dke.swag.analysis_graphs.utils;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.Step;
import at.jku.dke.swag.analysis_graphs.Trace;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.*;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.md_elements.Dimension;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static Set<Update> evaluate(AnalysisSituation source, Set<Operation> operations) {
        Set<Update> updateSet = new HashSet<>();

        for (Operation op : operations) {
            updateSet.addAll(op.evaluate(source));
        }

        return updateSet;
    }

    /**
     * Evaluate a set of operations in analysis situation.
     * If any operations produces an empty update set, a RunTimeException is thrown
     *
     * @param source     source situation to evaluate operations in
     * @param operations set of operations to evaluate
     * @return a set of updates
     */
    public static Set<Update> evaluateAndCheck(AnalysisSituation source, Set<Operation> operations) {
        Set<Update> updateSet = new HashSet<>();

        for (Operation op : operations) {
            Set<Update> updates = op.evaluate(source);
            if (updates.isEmpty()) {
                throw new RuntimeException("Empty update sets not allowed in traces");
            }
            updateSet.addAll(op.evaluate(source));
        }

        return updateSet;
    }

    public static AnalysisSituation evaluateAndFire(AnalysisSituation source, Set<Operation> operations) {
        AnalysisSituation situation = fire(source, evaluate(source, operations));
        situation.AssertValidSituation();
        return situation;
    }

    public static AnalysisSituation evaluateAndFireNoCheck(AnalysisSituation source, Set<Operation> operations) {
        return fire(source, evaluate(source, operations));
    }

    public static AnalysisSituation compose(AnalysisSituation source, Set<Operation> operations1, Set<Operation> operations2) {
        return evaluateAndFire(evaluateAndFire(source, operations1), operations2);
    }

    public static Set<Update> conditionalUnion(AnalysisSituation source, Set<Operation> operations1, Set<Operation> operations2) {
        AnalysisSituation intermediate = Utils.evaluateAndFireNoCheck(source, operations1);

        Set<Update> updates1 = evaluate(source, operations1);
        Set<Update> updates2 = evaluate(intermediate, operations2);

        if (updates1.isEmpty() || updates2.isEmpty()) {
            return new HashSet<Update>();
        }

        Set<Update> updatesUnion = new HashSet<Update>();
        updatesUnion.addAll(updates1);
        updatesUnion.addAll(updates2);
        return updatesUnion;
    }

    private static AnalysisSituation fire(AnalysisSituation source, Set<Update> updateSet) {

        AnalysisSituation resultSituation = source.copy();

        for (Update update : updateSet) {
            resultSituation = fire(resultSituation, update);
        }
        return resultSituation;
    }

    public static AnalysisSituation fire(AnalysisSituation source, Update update) {

        AnalysisSituation resultSituation = source.copy();

        if (update.getLocation().isMeasureLocation()) {
            resultSituation.setMeasures((BindableSet) update.getValue());
        }

        if (update.getLocation().isResultFilterLocation()) {
            resultSituation.setResultFilter((BindableSet) update.getValue());
        }

        if (update.getLocation().isDimensionUpdate()) {
            String dimension = update.getLocation().getDimensionOfUpdate();

            if (update.getLocation().isGranLocation()) {
                resultSituation.setGran(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if (update.getLocation().isDiceLevelLocation()) {
                resultSituation.setDiceLevel(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if (update.getLocation().isDiceNodeLocation()) {
                resultSituation.setDiceNode(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if (update.getLocation().isDimensionSelectionLocation()) {
                resultSituation.setDimensionSelection(new Dimension(dimension), (BindableSet) update.getValue());
            }
        }

        return resultSituation;
    }

    public static ConstantOrUnknown actual(PairOrConstant elem) {
        if (elem.isConstantOrUnknown()) {
            return ((ConstantOrUnknown) elem);
        }
        return ((Pair) elem).getConstant();
    }

    public static Set<Parameter> unbound(BindableSet set) {
        return set.getElements().stream()
                .filter(e -> e.isPair() && ((Pair) e).getConstantOrUnknown().isUnknown())
                .map(e -> ((Pair) e).getParameter())
                .collect(Collectors.toSet());
    }

    public static Set<Pair> getAllPairsOver(Set<Parameter> params, Set<Constant> consts) {

        Set<Constant> constsNew = new HashSet<>(consts);
        constsNew.add(Constant.unknown());

        Set<Pair> pairs = new HashSet<>();
        for (Parameter par : params) {
            for (Constant con : constsNew) {
                try {
                    pairs.add(new Pair(par, con));
                } catch (Exception ex) {
                    // DO nothing
                }
            }
        }
        return pairs;
    }

    public static Set<BindableSet> getAllBindableSetsOver(Set<Parameter> params, Set<Constant> consts) {

        Set<BindableSet> S = new HashSet<>();

        Set<Pair> pairs = getAllPairsOver(params, consts);
        Set<PairOrConstant> all = new HashSet<>(pairs);
        all.addAll(new HashSet<>(consts));

        Set<Set<PairOrConstant>> sets = Sets.powerSet(all);

        for (Set<PairOrConstant> set : sets) {
            BindableSet s = new BindableSet(set);
            if (s.verifyValid()) {
                S.add(s);
            }
        }

        return S;
    }

    public static Set<PairOrConstant> getAllPairsAndConstantsOver(Set<Parameter> params, Set<Constant> consts) {
        Set<Pair> pairs = getAllPairsOver(params, consts);
        Set<PairOrConstant> all = new HashSet<>(pairs);
        all.addAll(new HashSet<>(consts));
        return all;
    }

    public static Set<Parameter> restrictParamsTo(
            Set<Parameter> params,
            Set<Constant> consts,
            Map<Parameter, Set<Constant>> domain) {
        return params.stream()
                .filter(p -> consts.containsAll(domain.get(p)))
                .collect(Collectors.toSet());
    }

    public static Set<BindableSet> restrictBindableSetsTo(
            Set<BindableSet> S,
            Set<Parameter> allParas,
            Set<Constant> constsToRestrictTo,
            Map<Parameter, Set<Constant>> domain) {

        return S.stream()
                .filter(s -> getAllBindableSetsOver(
                        restrictParamsTo(allParas, constsToRestrictTo, domain), constsToRestrictTo)
                        .contains(s)
                )
                .collect(Collectors.toSet());
    }

    public static Set<PairOrConstant> restrictPairsAndConstantsTo(
            Set<Parameter> allParas,
            Set<Constant> allConsts,
            Set<Constant> constsToRestrictTo,
            Map<Parameter, Set<Constant>> domain) {

        Set<PairOrConstant> allPairsOrConstants = getAllPairsAndConstantsOver(allParas, allConsts);
        Set<PairOrConstant> res = new HashSet<>();
        Set<Parameter> restrictedParas = restrictParamsTo(allParas, constsToRestrictTo, domain);
        return getAllPairsAndConstantsOver(restrictedParas, constsToRestrictTo);
    }

    public static Step bind(Step step, Map<Operation, OperationBinding> bindings) {

        Step newStep = new Step(null, null, new HashSet<>());

        for (Operation op : step.getOperations()) {
            newStep.getOperations().add(bind(op, bindings.get(op)));
        }

        return newStep;
    }

    public static Operation bind(Operation operation, OperationBinding binding) {

        Operation newOperation = operation.copy();
        applyBindingTo(newOperation, binding);
        return newOperation;
    }

    public static AnalysisSituation bind(AnalysisSituation source, Set<SituationBinding> bindings) {

        AnalysisSituation resultSituation = source.copy();

        for (SituationBinding binding : bindings) {
            resultSituation = bind(resultSituation, binding);
        }

        resultSituation.AssertValidSituation();
        return resultSituation;
    }

    public static AnalysisSituation bind(AnalysisSituation source, SituationBinding binding) {

        AnalysisSituation resultSituation = source.copy();

        if (binding.getLocation().isMeasureLocation()) {
            applyBindingTo(resultSituation.getMeasures(), binding);
        }

        if (binding.getLocation().isResultFilterLocation()) {
            applyBindingTo(resultSituation.getResultFilters(), binding);
        }

        if (binding.getLocation().isDimensionUpdate()) {
            String dimension = binding.getLocation().getDimensionOfUpdate();

            if (binding.getLocation().isGranLocation()) {
                applyBindingTo(resultSituation.getGranularities().get(new Dimension(dimension)), binding);
            }

            if (binding.getLocation().isDiceLevelLocation()) {
                applyBindingTo(resultSituation.getDiceLevels().get(new Dimension(dimension)), binding);
            }

            if (binding.getLocation().isDiceNodeLocation()) {
                applyBindingTo(resultSituation.getDiceNodes().get(new Dimension(dimension)), binding);
            }

            if (binding.getLocation().isDimensionSelectionLocation()) {
                applyBindingTo(resultSituation.getDimensionSelection().get(new Dimension(dimension)), binding);
            }
        }

        return resultSituation;
    }

    private static void applyBindingTo(BindableSet set, SituationBinding binding) {

        for (Parameter param : binding.getBindings().keySet()) {
            Optional<Pair> pairOfParameter = set.getPairOfParameter(param);
            if (set.paras().contains(param) &&
                    pairOfParameter.isPresent() &&
                    pairOfParameter.get().getConstant().isUnknown()) {
                
                pairOfParameter.ifPresent(x -> {
                    set.setDifference(x);
                    Pair newPair = x.copy();
                    newPair.setConstant(binding.getBindings().get(param));
                    set.union(newPair);
                });
            }
        }
    }

    private static void applyBindingTo(PairOrConstant elem, SituationBinding binding) {
        for (Parameter param : binding.getBindings().keySet()) {
            if (elem.isPair()) {
                Pair newElm = (Pair) elem;
                if (newElm.getConstant().isUnknown()) {
                    newElm.setConstant(binding.getBindings().get(param));
                }
            }
        }
    }

    private static void applyBindingTo(Operation operation, OperationBinding binding) {
        for (Integer i : binding.getBindings().keySet()) {
            if (((int) i) <= operation.getParameters().size()) {
                operation.getParameters().set(i - 1, binding.getBindings().get(i));
            }
        }
    }

    public static List<AnalysisSituation> executeTrace(Trace trace) {

        List<AnalysisSituation> situations = new ArrayList<>();

        AnalysisSituation initialAsPrime = Utils.bind(trace.getInitialAs(), trace.getInitialAsBindings());
        initialAsPrime.AssertValidSituation();
        situations.add(initialAsPrime);

        for (int i = 0; i < trace.getSteps().size(); i++) {
            Step stepPrime = Utils.bind(trace.getSteps().get(i), trace.getStepBindings().get(i));
            Set<Update> updates = Utils.evaluateAndCheck(initialAsPrime, stepPrime.getOperations());
            initialAsPrime = Utils.fire(initialAsPrime, updates);
            initialAsPrime.AssertValidSituation();
            situations.add(initialAsPrime);
        }

        return situations;
    }
}
