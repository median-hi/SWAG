package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class OperationTypes {

    public static final MDGraph mdGraph = MDGraphInit.initMDGraph();
    List<Object> params = new ArrayList<>();

    public OperationTypes(List<Object> params) {
        this.params = params;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public abstract Set<Update> updSet(AnalysisSituation situation, List<Object> params);
}
