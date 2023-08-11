package at.jku.dke.swag;

import at.jku.dke.swag.md_data.MDData;
import at.jku.dke.swag.md_elements.*;
import at.jku.dke.swag.sparql.MDQuerySparqlGenerator;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmdTest {

    Fact fact = new Fact("film");
    Dimension directiontDim = new Dimension("directionDim");
    Hierarchy directionHier = new Hierarchy("directionHier");
    Level director = new Level("director");
    Level gender = new Level("gender");
    Level topDirection = new Level("top_direction");
    Dimension contenttDim = new Dimension("contentDim");
    Hierarchy contentHier = new Hierarchy("contentHier");
    Level genre = new Level("genre");
    Level topContent = new Level("top_content");
    Dimension origintDim = new Dimension("origintDim");
    Hierarchy originHier = new Hierarchy("originHier");
    Level country = new Level("country");
    Level continent = new Level("continent");
    Level topOrigin = new Level("top_origin");
    Dimension timeDim = new Dimension("timeDim");
    Hierarchy timeHierarchy = new Hierarchy("timeHierarchy");
    Level date = new Level("date");
    Level year = new Level("year");
    Level topTime = new Level("top_time");
    Measure boxOffice = new Measure("boxOffice");

    @org.junit.jupiter.api.Test
    void testExample11() {

        MDGraphAndMap mappedGraph = MDGraphInit.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(director, country));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }

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

    @Test
    void checkSetsOfMdSchema() {

        MDGraphAndMap md = MDGraphInit.initMDGraphAndMap();
        md.excludeTops();

        Assertions.assertEquals(Set.of(country, continent, director, gender, date, year, genre), md.getGraph().getL());

        Set<RollUpPair> rolls = new HashSet<>();
        rolls.add(new RollUpPair(country, continent));
        rolls.add(new RollUpPair(director, gender));
        rolls.add(new RollUpPair(date, year));

        MdGraphSMD gSmd = md.createGraph();

        Assertions.assertEquals(rolls, md.getGraph().getLL());
    }
}
