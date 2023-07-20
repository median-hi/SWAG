package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.MdGraphSMD;
import at.jku.dke.swag.md_data.MDData;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MDGraphAndMap {

    MDGraph graph;
    MappedMDGraph map;

    MDData data;


    public MDGraphAndMap(MDGraph graph, MappedMDGraph map, MDData data) {
        this.graph = graph;
        this.map = map;
        this.data = data;
    }

    public MDGraph getGraph() {
        return graph;
    }

    public void setGraph(MDGraph graph) {
        this.graph = graph;
    }

    public MappedMDGraph getMap() {
        return map;
    }

    public void setMap(MappedMDGraph map) {
        this.map = map;
    }

    public MDData getData() {
        return data;
    }

    public void setData(MDData data) {
        this.data = data;
    }

    public void excludeTops() {
        this.getGraph().getLL().removeIf(l -> l.getTo().getUri().contains("top_"));
        this.getGraph().getL().removeIf(l -> l.getUri().contains("top_"));
    }

    public MdGraphSMD createGraph() {
        MdGraphSMD graph = new MdGraphSMD();
        Set<MDElement> elms = Set.of((MDElement) getGraph().getF().stream().findAny().get());
        elms = new HashSet<>(elms);
        elms.addAll(getGraph().getL().stream().map(l -> (MDElement) l).collect(Collectors.toSet()));
        graph.setNodes(elms);

        for (RollUpPair pair : getGraph().getLL()) {
            graph.addEdge(pair.getFrom(), pair.getTo());
        }

        for (Set<Level> levels : getGraph().getFL().values()) {
            for (Level l : levels) {
                graph.addEdge(getGraph().getF().stream().findAny().get(), l);
            }
        }
        return graph;
    }

}
