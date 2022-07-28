package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.Copiable;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;

import java.util.*;

public class Operation implements Copiable {

    private OperationTypes type;
    private List<Object> parameters;

    public Operation(OperationTypes type, List<Object> parameters) {
        this.type = type;
        this.parameters = parameters;
    }


    public Set<Update> evaluate(AnalysisSituation source){
        return getType().updSet(source, parameters);
    }

    public OperationTypes getType() {
        return type;
    }

    public void setType(OperationTypes type) {
        this.type = type;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(type, operation.type) && Objects.equals(parameters, operation.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, parameters);
    }

    @Override
    public Operation copy() {
        List<Object> args = new ArrayList<>(this.getParameters());
        return new Operation(this.getType(), args);
    }
}
