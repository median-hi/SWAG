package at.jku.dke.swag;

import at.jku.dke.swag.md_elements.MDElement;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MdGraphSMD {

    private Set<MDElement> nodes = new HashSet<>();
    private Set<Map.Entry<MDElement, MDElement>> edges = new HashSet<>();

    public MdGraphSMD() {
        super();
    }

    public MdGraphSMD(Set<MDElement> nodes, Set<Map.Entry<MDElement, MDElement>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public void addNode(MDElement e) {
        this.getNodes().add(e);
    }

    public void addEdge(MDElement e1, MDElement e2) {
        this.getEdges().add(new AbstractMap.SimpleEntry(e1, e2));
    }

    public Set<MDElement> getNodes() {
        return nodes;
    }

    public void setNodes(Set<MDElement> nodes) {
        this.nodes = nodes;
    }

    public Set<Map.Entry<MDElement, MDElement>> getEdges() {
        return edges;
    }

    public void setEdges(Set<Map.Entry<MDElement, MDElement>> edges) {
        this.edges = edges;
    }

}
