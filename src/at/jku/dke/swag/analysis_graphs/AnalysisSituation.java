package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.MDElems.LevelMember;
import at.jku.dke.swag.MDElems.MDGraph;

import java.util.HashMap;
import java.util.Map;

public class AnalysisSituation implements Copiable{


    private MDGraph mdGraph;

    public AnalysisSituation(MDGraph mdGraph) {
        this.mdGraph = mdGraph;

        for (Dimension d : mdGraph.getD()){
            this.getGranularities().put(d, mdGraph.top(d));
            this.getDiceLevels().put(d, mdGraph.top(d));
            this.getDiceNodes().put(d, new LevelMember("all_"+d.getUri()));
            this.getDimensionSelection().put(d, BindableSet.empty());
        }

    }

    BindableSet measures = new BindableSet();
    BindableSet resultFilters = new BindableSet();

    Map<Dimension, BindableSet > dimensionSelection = new HashMap<>();
    Map<Dimension, PairOrConstant> granularities  = new HashMap<>();
    Map<Dimension, PairOrConstant> diceLevels  = new HashMap<>();
    Map<Dimension, PairOrConstant> diceNodes  = new HashMap<>();

    public AnalysisSituation setMeasures (BindableSet measures){
        this.measures = measures;
        return this;
    }

    public AnalysisSituation setResultFilter (BindableSet resultFilters){
        this.resultFilters = resultFilters;
        return this;
    }

    public AnalysisSituation setDimensionSelection(Dimension d, BindableSet conds){
        dimensionSelection.put(d, conds);
        return this;
    }

    public AnalysisSituation setGran(Dimension d, PairOrConstant gran){
        granularities.put(d, gran);
        return this;
    }

    public AnalysisSituation setDiceLevel(Dimension d, PairOrConstant level){
        diceLevels.put(d, level);
        return this;
    }

    public AnalysisSituation setDiceNode(Dimension d, PairOrConstant node){
        diceNodes.put(d, node);
        return this;
    }


    @Override
    public AnalysisSituation copy() {

       AnalysisSituation newSituation = new AnalysisSituation(this.getMdGraph());

       newSituation.setMeasures(measures.copy());
       newSituation.setResultFilter(resultFilters.copy());

       for(Dimension d : mdGraph.getD()){
           newSituation.dimensionSelection.put(d, this.dimensionSelection.get(d));
           newSituation.granularities.put(d, this.granularities.get(d));
           newSituation.diceLevels.put(d, this.diceLevels.get(d));
           newSituation.diceNodes.put(d, this.diceNodes.get(d));
       }

       return newSituation;
    }



    public MDGraph getMdGraph() {
        return mdGraph;
    }

    public void setMdGraph(MDGraph mdGraph) {
        this.mdGraph = mdGraph;
    }

    public BindableSet getMeasures() {
        return measures;
    }

    public BindableSet getResultFilters() {
        return resultFilters;
    }

    public void setResultFilters(BindableSet resultFilters) {
        this.resultFilters = resultFilters;
    }

    public Map<Dimension, BindableSet> getDimensionSelection() {
        return dimensionSelection;
    }

    public void setDimensionSelection(Map<Dimension, BindableSet> dimensionSelection) {
        this.dimensionSelection = dimensionSelection;
    }

    public Map<Dimension, PairOrConstant> getGranularities() {
        return granularities;
    }

    public void setGranularities(Map<Dimension, PairOrConstant> granularities) {
        this.granularities = granularities;
    }

    public Map<Dimension, PairOrConstant> getDiceLevels() {
        return diceLevels;
    }

    public void setDiceLevels(Map<Dimension, PairOrConstant> diceLevels) {
        this.diceLevels = diceLevels;
    }

    public Map<Dimension, PairOrConstant> getDiceNodes() {
        return diceNodes;
    }

    public void setDiceNodes(Map<Dimension, PairOrConstant> diceNodes) {
        this.diceNodes = diceNodes;
    }


}

