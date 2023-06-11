package at.jku.dke.swag;

import at.jku.dke.swag.md_data.MDData;
import at.jku.dke.swag.md_elements.*;
import at.jku.dke.swag.sparql.MDQuerySparqlGenerator;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

    @org.junit.jupiter.api.Test
    public void test1() {
        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();

        List<Level> groupBy = List.of(new Level("country"));
        Measure m = new Measure("boxOffice");
        MDQuerySparqlGenerator sparqlGen = new MDQuerySparqlGenerator(mdGraph, mdMap);
        String queryStr = sparqlGen.makeAggregationQuery(mdGraph.getFact(), m, groupBy);
        System.out.println(queryStr);
        Query query = QueryFactory.create(queryStr);
        System.out.println(query.toString());
    }

    @org.junit.jupiter.api.Test
    public void test2() {
        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();

        List<Level> groupBy = List.of(new Level("country"), new Level("director"));
        MDQuerySparqlGenerator sparqlGen = new MDQuerySparqlGenerator(mdGraph, mdMap);
        String queryStr = sparqlGen.makeDimensionsQuery(mdGraph.getFact(), groupBy);
        System.out.println(queryStr);
        Query query = QueryFactory.create(queryStr);
        System.out.println(query.toString());
    }

    @org.junit.jupiter.api.Test
    public void test3() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(new Level("genre")));

        PrintUtils.printMap(factsAndCoordinates);

    }

    @org.junit.jupiter.api.Test
    public void test4() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(new Level("genre")));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));

    }

    @org.junit.jupiter.api.Test
    public void test5() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(new Level("genre")));

        SummarizabilityOptions summ = new SummarizabilityOptions(mdGraph, data);
        Map<String, Double> factsAndWeights = summ.getSplitWeightPerGroup(factsAndCoordinates);
        PrintUtils.printMap1(factsAndWeights);
    }

    @org.junit.jupiter.api.Test
    public void test6() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(new Level("genre")));

        SummarizabilityOptions summ = new SummarizabilityOptions(mdGraph, data);
        Map<String, Double> factsAndWeights = summ.getSplitWeightPerGroup(factsAndCoordinates);
        Map<List<String>, Double> aggs = summ.aggregateSplit(
                factsAndCoordinates,
                factsAndWeights,
                new Measure("boxOffice"));
        PrintUtils.printMap2(aggs);
    }

    @org.junit.jupiter.api.Test
    public void test7() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSetTakeOne(
                data, mdGraph,
                List.of(new Level("genre")));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }

    @org.junit.jupiter.api.Test
    public void testDimDiscardMissing() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(new Level("date")));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }

    @org.junit.jupiter.api.Test
    public void testDimDefaultValue() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSetDefaultValue(
                data, mdGraph,
                List.of(new Level("date")));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }

}
