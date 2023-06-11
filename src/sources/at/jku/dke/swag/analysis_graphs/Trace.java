package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.OperationBinding;
import at.jku.dke.swag.analysis_graphs.basic_elements.SituationBinding;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;

import java.util.*;

public class Trace {

    AnalysisSituation initialAs;
    SituationBinding initialAsBindings;
    List<Step> steps = new LinkedList<>();
    List<Map<Operation, OperationBinding>> stepBindings = new ArrayList<>();

    public Trace(AnalysisSituation initialAs, SituationBinding initialAsBindings, List<Step> steps, List<Map<Operation, OperationBinding>> stepBindings) {
        this.initialAs = initialAs;
        this.initialAsBindings = initialAsBindings;
        this.steps = steps;
        this.stepBindings = stepBindings;
    }

    public AnalysisSituation getInitialAs() {
        return initialAs;
    }

    public void setInitialAs(AnalysisSituation initialAs) {
        this.initialAs = initialAs;
    }

    public SituationBinding getInitialAsBindings() {
        return initialAsBindings;
    }

    public void setInitialAsBindings(SituationBinding initialAsBindings) {
        this.initialAsBindings = initialAsBindings;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Map<Operation, OperationBinding>> getStepBindings() {
        return stepBindings;
    }

    public void setStepBindings(List<Map<Operation, OperationBinding>> stepBindings) {
        this.stepBindings = stepBindings;
    }

}
