package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.analysis_graphs.basic_elements.OperationBinding;
import at.jku.dke.swag.analysis_graphs.basic_elements.SituationBinding;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Trace {

    AnalysisSituation initialAs;
    SituationBinding initialAsBindings;
    List<Step> steps = new LinkedList<>();
    List<Map<Operation, OperationBinding>> stepBindings = new ArrayList<>();

    public Trace(AnalysisSituation initialAs,
                 SituationBinding initialAsBindings,
                 List<Step> steps,
                 List<Map<Operation, OperationBinding>> stepBindings) {
        this.initialAs = initialAs;
        this.initialAsBindings = initialAsBindings;
        this.steps = steps;
        this.stepBindings = stepBindings;
        assertValidTrace();
    }

    private Trace(AnalysisSituation initialAs,
                  SituationBinding initialAsBindings,
                  List<Step> steps) {
        this.initialAs = initialAs;
        this.initialAsBindings = initialAsBindings;
        this.steps = steps;
    }


    public static Trace initStrategyOneTrace(AnalysisSituation initialAs,
                                             SituationBinding initialAsBindings,
                                             List<Step> steps) {
        return new Trace(initialAs, initialAsBindings, steps);
    }

    public static Trace initStrategyTwoTrace(AnalysisSituation initialAs,
                                             SituationBinding initialAsBindings) {
        return new Trace(initialAs, initialAsBindings, new ArrayList<>());
    }

    public void addStepBinding(Step step, Map<Operation, OperationBinding> binding) {
        stepBindings.add(steps.indexOf(step), binding);
        checkTrace();
    }

    public void addStepAndBinding(Step step, Map<Operation, OperationBinding> binding) {
        steps.add(step);
        stepBindings.add(binding);
        checkTrace();
    }

    private void checkTrace() {
        AnalysisSituation initialAsPrime = Utils.bind(this.getInitialAs(), this.getInitialAsBindings());
        for (int i = 0; i < this.getStepBindings().size(); i++) {
            Step stepPrime = Utils.bind(this.getSteps().get(i), this.getStepBindings().get(i));
            Utils.evaluateAndCheck(initialAsPrime, stepPrime.getOperations());
            initialAsPrime = Utils.evaluateAndFire(initialAsPrime, stepPrime.getOperations());
            Utils.assertSemanticsPreservingStep(initialAsPrime, this.getSteps().get(i).getTarget());
        }
    }

    public AnalysisSituation getInitialAs() {
        return initialAs;
    }

    private void setInitialAs(AnalysisSituation initialAs) {
        this.initialAs = initialAs;
    }

    public SituationBinding getInitialAsBindings() {
        return initialAsBindings;
    }

    private void setInitialAsBindings(SituationBinding initialAsBindings) {
        this.initialAsBindings = initialAsBindings;
    }

    public List<Step> getSteps() {
        return steps;
    }

    private void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Map<Operation, OperationBinding>> getStepBindings() {
        return stepBindings;
    }

    private void setStepBindings(List<Map<Operation, OperationBinding>> stepBindings) {
        this.stepBindings = stepBindings;
    }

    private void assertValidTrace() {
        AnalysisSituation initialAsPrime = Utils.bind(this.getInitialAs(), this.getInitialAsBindings());

        for (int i = 0; i < this.getStepBindings().size(); i++) {
            Step stepPrime = Utils.bind(this.getSteps().get(i), this.getStepBindings().get(i));
            Utils.evaluateAndCheck(initialAsPrime, stepPrime.getOperations());
            initialAsPrime = Utils.evaluateAndFire(initialAsPrime, stepPrime.getOperations());
            Utils.assertSemanticsPreservingStep(initialAsPrime, this.getSteps().get(i).getTarget());
        }
    }

}
