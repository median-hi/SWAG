package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;

import java.util.*;

public class Step implements Copiable {
    AnalysisSituation source;
    AnalysisSituation target;
    Set<Operation> operations = new HashSet<>();

    public Step(AnalysisSituation source, AnalysisSituation target, Set<Operation> operations) {
        this.source = source;
        this.target = target;
        this.operations = operations;
        assertValidStep();
    }

    public <T extends OperationTypes> Operation getOperationOfType(Class<T> clz) {
        return operations.stream().filter(op -> op.getType().getClass().equals(clz)).findAny().orElseThrow();
    }

    public AnalysisSituation getSource() {
        return source;
    }

    public void setSource(AnalysisSituation source) {
        this.source = source;
    }

    public AnalysisSituation getTarget() {
        return target;
    }

    public void setTarget(AnalysisSituation target) {
        this.target = target;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
        assertValidStep();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return Objects.equals(source, step.source) && Objects.equals(target, step.target) && Objects.equals(operations, step.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, operations);
    }

    @Override
    public Copiable copy() {
        Set<Operation> copiedOperations = new HashSet<>();
        for (Operation op : this.getOperations()) {
            copiedOperations.add(op.copy());
        }
        return new Step(null, null, copiedOperations);
    }

    private void assertValidStep() {
        assertDifferentOperationTypes();
    }

    private void assertDifferentOperationTypes() {
        List<Class<? extends OperationTypes>> ops = new ArrayList<>();

        for (Operation op : operations) {
            ops.add(op.getType().getClass());
        }

        if (ops.size() != (new HashSet<>(ops)).size()) {
            throw new RuntimeException("Duplicate operation type in step");
        }
    }
}
