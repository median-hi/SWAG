package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.analysis_graphs.operations.Operation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Step implements  Copiable{
    AnalysisSituation source;
    AnalysisSituation target;
    Set<Operation> operations = new HashSet<>();

    public AnalysisSituation getSource() {
        return source;
    }

    public Step(AnalysisSituation source, AnalysisSituation target, Set<Operation> operations) {
        this.source = source;
        this.target = target;
        this.operations = operations;
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
        for (Operation op: this.getOperations()){
            copiedOperations.add(op.copy());
        }
        return new Step(null, null, copiedOperations);
    }
}
