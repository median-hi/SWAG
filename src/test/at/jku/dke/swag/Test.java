package at.jku.dke.swag;

import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.MDGraphAndMap;
import at.jku.dke.swag.md_elements.MappedMDGraph;
import at.jku.dke.swag.sparql.MDQuerySparqlGenerator;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;

import java.util.List;

public class Test {

    @org.junit.Test
    public void test(){
        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();

        List<Level> groupBy = List.of(new Level("continent"), new Level("director"));
        MDQuerySparqlGenerator sparqlGen = new MDQuerySparqlGenerator(mdGraph, mdMap);
        String queryStr = sparqlGen.makeGroupByQuery(groupBy);
        System.out.println(queryStr);
        Query query = QueryFactory.create(queryStr);
        System.out.println(query.toString());

    }
}
