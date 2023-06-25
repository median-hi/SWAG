package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.MDGraph;

import java.util.List;

public class SWAG {
    MDGraph mdGraph;

    List<AnalysisSituation> situations;
    List<Step> steps;

    public SWAG(MDGraph mdGraph, List<AnalysisSituation> situations, List<Step> steps) {
        this.mdGraph = mdGraph;
        this.situations = situations;
        this.steps = steps;
        assertValidSwag();
    }

    public MDGraph getMdGraph() {
        return mdGraph;
    }

    public void setMdGraph(MDGraph mdGraph) {
        this.mdGraph = mdGraph;
    }

    public List<AnalysisSituation> getSituations() {
        return situations;
    }

    public void setSituations(List<AnalysisSituation> situations) {
        this.situations = situations;
        assertValidSwag();
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        assertValidSwag();
    }

    private void assertValidSwag() {
        for (Step s : getSteps()) {
            if (Utils.evaluate(s.getSource(), s.getOperations()).isEmpty()) {
                throw new RuntimeException("Operation creates an empty update set.");
            }
        }
    }
}

