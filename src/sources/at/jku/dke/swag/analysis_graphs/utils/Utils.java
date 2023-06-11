package at.jku.dke.swag.analysis_graphs.utils;

import at.jku.dke.swag.analysis_graphs.Step;
import at.jku.dke.swag.analysis_graphs.Trace;
import at.jku.dke.swag.analysis_graphs.basic_elements.*;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.operations.Operation;

import java.util.*;

public class Utils {

    public static Set <Update> evaluate(AnalysisSituation source, Set<Operation> operations){
        Set <Update> updateSet = new HashSet<>();

        for (Operation op : operations){
            updateSet.addAll(op.evaluate(source));
        }

        return updateSet;
    }

    public static AnalysisSituation evaluateAndFire(AnalysisSituation source, Set<Operation> operations){
        return fire (source, evaluate(source, operations));
    }

    public static  AnalysisSituation fire(AnalysisSituation source, Set<Update> updateSet){

        AnalysisSituation resultSituation = source.copy();

        for (Update update : updateSet){
            resultSituation = fire(resultSituation, update);
        }

        return resultSituation;
    }

    public static  AnalysisSituation fire(AnalysisSituation source, Update update){

        AnalysisSituation resultSituation = source.copy();

        if(update.getLocation().isMeasureLocation()){
            resultSituation.setMeasures((BindableSet) update.getValue());
        }

        if(update.getLocation().isResultFilterLocation()){
            resultSituation.setResultFilter((BindableSet) update.getValue());
        }

        if(update.getLocation().isDimensionUpdate()){
            String dimension = update.getLocation().getDimensionOfUpdate();

            if(update.getLocation().isGranLocation()){
                resultSituation.setGran(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if(update.getLocation().isDiceLevelLocation()){
                resultSituation.setDiceLevel(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if(update.getLocation().isDiceNodeLocation()){
                resultSituation.setDiceNode(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if(update.getLocation().isDimensionSelectionLocation()){
                resultSituation.setDimensionSelection(new Dimension(dimension), (BindableSet) update.getValue());
            }
        }

        return resultSituation;
    }

    public static ConstantOrUnknown actual(PairOrConstant elem){
        if(elem.isConstantOrUnknown()){
            return ((ConstantOrUnknown) elem);
        }
        return ((Pair) elem).getConstant();
    }

    public static Step bind(Step step, Map<Operation, OperationBinding> bindings){

        Step newStep = new Step(null, null, new HashSet<>());

        for(Operation op: step.getOperations()){
            newStep.getOperations().add(bind(op, bindings.get(op)));
        }

        return newStep;
    }

    public static Operation bind(Operation operation, OperationBinding binding){

        Operation newOperation = operation.copy();
        applyBindingTo(newOperation, binding);
        return newOperation;
    }

    public static  AnalysisSituation bind(AnalysisSituation source, Set<SituationBinding> bindings) {
        AnalysisSituation resultSituation = source.copy();

        for (SituationBinding binding : bindings){
            resultSituation = bind(resultSituation, binding);
        }

        return resultSituation;
    }

    public static  AnalysisSituation bind(AnalysisSituation source, SituationBinding binding){

        AnalysisSituation resultSituation = source.copy();

        if(binding.getLocation().isMeasureLocation()){
            applyBindingTo(resultSituation.getMeasures(), binding);
        }

        if(binding.getLocation().isResultFilterLocation()){
            applyBindingTo(resultSituation.getResultFilters(), binding);
        }

        if(binding.getLocation().isDimensionUpdate()){
            String dimension = binding.getLocation().getDimensionOfUpdate();

            if(binding.getLocation().isGranLocation()){
                applyBindingTo(resultSituation.getGranularities().get(new Dimension(dimension)), binding);
            }

            if(binding.getLocation().isDiceLevelLocation()){
                applyBindingTo(resultSituation.getDiceLevels().get(new Dimension(dimension)), binding);
            }

            if(binding.getLocation().isDiceNodeLocation()){
                applyBindingTo(resultSituation.getDiceNodes().get(new Dimension(dimension)), binding);
            }

            if(binding.getLocation().isDimensionSelectionLocation()){
                applyBindingTo(resultSituation.getDimensionSelection().get(new Dimension(dimension)), binding);
            }
        }

        return resultSituation;
    }

    private static void applyBindingTo(BindableSet set, SituationBinding binding){
        for (Parameter param: binding.getBindings().keySet()){

            Optional<Pair> pairOfParameter = set.getPairOfParameter(param);

            if (set.paras().contains(param) && pairOfParameter.isPresent() && pairOfParameter.get().getConstant().isStrictlyUnknown()){
                pairOfParameter.ifPresent(x -> x.setConstant(binding.getBindings().get(param)));
            }
        }
    }

    private static  void applyBindingTo(PairOrConstant elem, SituationBinding binding){
        for (Parameter param: binding.getBindings().keySet()){
            if (elem.isPair()){
                Pair newElm = (Pair) elem;
                if(newElm.getConstant().isStrictlyUnknown()) {
                    newElm.setConstant(binding.getBindings().get(param));
                }
            }
        }
    }

    private static void applyBindingTo(Operation operation, OperationBinding binding){
        for (Integer i : binding.getBindings().keySet()){
            if(((int) i) <= operation.getParameters().size()){
                operation.getParameters().set(i-1, binding.getBindings().get(i));
            }
        }
    }

    public static List<AnalysisSituation> executeTrace(Trace trace){
        List<AnalysisSituation> situations = new ArrayList<>();

        AnalysisSituation initialAsPrime = Utils.bind(trace.getInitialAs(), trace.getInitialAsBindings());
        situations.add(initialAsPrime);

        for (int i = 0; i < trace.getSteps().size(); i++){
            Step stepPrime = Utils.bind(trace.getSteps().get(i), trace.getStepBindings().get(i));
            Set<Update> updates = Utils.evaluate(initialAsPrime, stepPrime.getOperations());
            initialAsPrime = Utils.fire(initialAsPrime, updates);
            situations.add(initialAsPrime);
        }

        return situations;
    }
}
