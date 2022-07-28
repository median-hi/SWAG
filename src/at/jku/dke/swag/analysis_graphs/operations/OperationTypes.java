package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.MDElems.MDGraph;
import at.jku.dke.swag.MDElems.init.MDGraphInit;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.Update;
import at.jku.dke.swag.analysis_graphs.init.OperationsTypesInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public abstract class OperationTypes {


    public OperationTypes(List<Object> params) {
        this.params = params;
    }

    List<Object> params = new ArrayList<>();

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public static final MDGraph mdGraph = MDGraphInit.initMDGraph();


    abstract public Set<Update> updSet(AnalysisSituation situation, List<Object> params);
}
