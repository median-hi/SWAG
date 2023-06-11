package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.md_data.MDData;

public class MDGraphAndMap {

    MDGraph graph;
    MappedMDGraph map;

    MDData data;


    public MDGraphAndMap(MDGraph graph, MappedMDGraph map, MDData data) {
        this.graph = graph;
        this.map = map;
        this.data = data;
    }

    public void setGraph(MDGraph graph) {
        this.graph = graph;
    }

    public void setMap(MappedMDGraph map) {
        this.map = map;
    }

    public MDGraph getGraph() {
        return graph;
    }

    public MappedMDGraph getMap() {
        return map;
    }


    public MDData getData() {
        return data;
    }

    public void setData(MDData data) {
        this.data = data;
    }
}
