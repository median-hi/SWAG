package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;

import java.util.List;
import java.util.Set;

public class SWAG {
    MDGraph mdGraph;

    List<AnalysisSituation> situations;
    List<Step> steps;

    public SWAG(MDGraph mdGraph, List<AnalysisSituation> situations, List<Step> steps) {
        this.mdGraph = mdGraph;
        this.situations = situations;
        this.steps = steps;
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
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}

