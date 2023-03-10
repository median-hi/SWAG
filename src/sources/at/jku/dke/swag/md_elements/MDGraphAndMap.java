package at.jku.dke.swag.md_elements;

public class MDGraphAndMap {

    MDGraph graph;
    MappedMDGraph map;

    public MDGraphAndMap(MDGraph graph, MappedMDGraph map) {
        this.graph = graph;
        this.map = map;
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
}
