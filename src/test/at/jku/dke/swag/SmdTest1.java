package at.jku.dke.swag;

import at.jku.dke.swag.md_data.MDData;
import at.jku.dke.swag.md_elements.*;
import at.jku.dke.swag.sparql.MDQuerySparqlGenerator;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmdTest1 {

    private static final String PREFIXES = "PREFIX p: <http://www.wikidata.org/prop/>\n" +
            "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n" +
            "PREFIX pqn: <http://www.wikidata.org/prop/qualifier/value-normalized/>\n" +
            "PREFIX pqv: <http://www.wikidata.org/prop/qualifier/value/>\n" +
            "PREFIX pr: <http://www.wikidata.org/prop/reference/>\n" +
            "PREFIX prn: <http://www.wikidata.org/prop/reference/value-normalized/>\n" +
            "PREFIX prov: <http://www.w3.org/ns/prov#>\n" +
            "PREFIX prv: <http://www.wikidata.org/prop/reference/value/>\n" +
            "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n" +
            "PREFIX psn: <http://www.wikidata.org/prop/statement/value-normalized/>\n" +
            "PREFIX psv: <http://www.wikidata.org/prop/statement/value/>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX schema: <http://schema.org/>\n" +
            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
            "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
            "PREFIX wdata: <http://www.wikidata.org/wiki/Special:EntityData/>\n" +
            "PREFIX wdno: <http://www.wikidata.org/prop/novalue/>\n" +
            "PREFIX wdref: <http://www.wikidata.org/reference/>\n" +
            "PREFIX wds: <http://www.wikidata.org/entity/statement/>\n" +
            "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
            "PREFIX wdtn: <http://www.wikidata.org/prop/direct-normalized/>\n" +
            "PREFIX wdv: <http://www.wikidata.org/value/>\n" +
            "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

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
    @DisplayName("Example 10")
    public void testDimensionQuery() {
        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
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
    @DisplayName("Example 10")
    public void testDimensionQuery1() {
        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();

        MDQuerySparqlGenerator sparqlGen = new MDQuerySparqlGenerator(mdGraph, mdMap);
        String queryStr = sparqlGen.makeMdPathQuery(mdGraph.getFact(), continent);
        System.out.println(queryStr);
        Query query = QueryFactory.create(queryStr);
        System.out.println(query.toString());

        Query expected = QueryFactory.create("\t SELECT DISTINCT ?film ?continent WHERE {\n" +
                "\t   {SELECT ?film WHERE {?film    <http://www.wikidata.org/prop/direct/P31>  <http://www.wikidata.org/entity/Q11424> . ?film <http://www.wikidata.org/prop/direct/P2142>  ?boxOffice1}}\n" +
                "\t   {SELECT ?film ?country WHERE {?film  <http://www.wikidata.org/prop/direct/P495>   ?country}}\n" +
                "\t   {SELECT ?country WHERE {?country    <http://www.wikidata.org/prop/direct/P31>  <http://www.wikidata.org/entity/Q6256>}}\n" +
                "\t   {SELECT ?country ?continent WHERE {?country <http://www.wikidata.org/prop/direct/P30>  ?continent}}\n" +
                "\t   {SELECT ?continent WHERE {?continent   <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q5107>}}\n" +
                "\t }");

        Assertions.assertEquals(expected, query);
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Example 12. Note that order in set may affect result of test")
    public void testBaseSetOfGroupingQuery() {
        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();

        List<Level> groupBy = List.of(director, continent);
        MDQuerySparqlGenerator sparqlGen = new MDQuerySparqlGenerator(mdGraph, mdMap);
        String queryStr = sparqlGen.makeBaseSetOfGroupingQuery(groupBy);
        System.out.println(queryStr);
        Query query = QueryFactory.create(queryStr);
        System.out.println(query.toString());

        String ss = "SELECT DISTINCT ?film ?director ?continent \n" +
                "\tWHERE{\n" +
                "\t\t\n" +
                "\t\t{SELECT ?film ?director \n" +
                "\t\t  WHERE {\n" +
                "\t\t    {SELECT ?film WHERE {?film   <http://www.wikidata.org/prop/direct/P31>   <http://www.wikidata.org/entity/Q11424>. ?film <http://www.wikidata.org/prop/direct/P2142>   ?boxOffice1}}\n" +
                "\t\t    {SELECT ?film ?director WHERE {?film  <http://www.wikidata.org/prop/direct/P57>   ?director}}\n" +
                "\t\t    {SELECT ?director WHERE {?director   <http://www.wikidata.org/prop/direct/P31>  <http://www.wikidata.org/entity/Q5>}}\n" +
                "\t\t  }} \n" +
                "\t\t{SELECT ?film ?continent \n" +
                "\t\t  WHERE {\n" +
                "\t\t    {SELECT ?film WHERE {?film   <http://www.wikidata.org/prop/direct/P31>    <http://www.wikidata.org/entity/Q11424> . ?film <http://www.wikidata.org/prop/direct/P2142>  ?boxOffice1}}\n" +
                "\t\t    {SELECT ?film ?country WHERE {?film   <http://www.wikidata.org/prop/direct/P495>   ?country}}\n" +
                "\t\t    {SELECT ?country WHERE {?country   <http://www.wikidata.org/prop/direct/P31>  <http://www.wikidata.org/entity/Q6256>}}\n" +
                "\t\t    {SELECT ?country ?continent WHERE {?country <http://www.wikidata.org/prop/direct/P30>  ?continent}}\n" +
                "\t\t    {SELECT ?continent WHERE {?continent  <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q5107>}}\n" +
                "\t\t  }  }\n" +
                "\t\t}\n";

        Query expected = QueryFactory.create(ss);

        Assertions.assertEquals(expected, query);
    }

    @DisplayName("Example 5")
    @Test
    void testMdPathInstance() {

        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getNonEmptyFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(continent));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }

    @DisplayName("Example 6")
    @Test
    void testGrouping() {

        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(country, director));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }

    @DisplayName("Example 8")
    @Test
    void testAggregation() {

        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getNonEmptyFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(country));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }

    @Test
    @DisplayName("Example 11")
    void testBaseSetOfGrouping() {

        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Map<List<String>, Set<String>> factsAndCoordinates = MDGraphUtils.getNonEmptyFactAndCoordinatesAsSet(
                data, mdGraph,
                List.of(director, country));

        PrintUtils.printMapAndMultiSet(factsAndCoordinates, mdGraph, data, new Measure("boxOffice"));
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Example 13. Note that order in set may affect result of test")
    public void testBaseSetOfMeasureQuery() {
        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();

        MDQuerySparqlGenerator sparqlGen = new MDQuerySparqlGenerator(mdGraph, mdMap);
        String queryStr = sparqlGen.makeMsrQuery(fact, boxOffice);
        System.out.println(queryStr);
        Query query = QueryFactory.create(queryStr);
        System.out.println(query.toString());

        String ss = "SELECT DISTINCT ?film ?boxOffice \n" +
                "\tWHERE{\n" +
                "\t  {SELECT ?film WHERE {?film    <http://www.wikidata.org/prop/direct/P31>  <http://www.wikidata.org/entity/Q11424> . ?film <http://www.wikidata.org/prop/direct/P2142>  ?boxOffice1}}\n" +
                "\t  {SELECT ?film ?boxOffice WHERE {\n" +
                "\t\t  ?film <http://www.wikidata.org/prop/P2142>  ?boxOfficeNode.\n" +
                "\t\t  ?boxOfficeNode <http://www.wikidata.org/prop/statement/value/P2142> ?value. \n" +
                "\t\t  ?value  <http://wikiba.se/ontology#quantityAmount> ?boxOffice.\n" +
                "\t\t  ?value <http://wikiba.se/ontology#quantityUnit>  <http://www.wikidata.org/entity/Q4917>.\n" +
                "\t\t  ?boxOfficeNode <http://www.wikidata.org/prop/qualifier/P3005> <http://www.wikidata.org/entity/Q13780930>.}\n" +
                "\t  }\n" +
                "\t}";

        Query expected = QueryFactory.create(ss);
        Assertions.assertEquals(expected, query);
    }


    @DisplayName("Example 15")
    @Test
    void testBaseSetOfAggregation() {

        MDGraphAndMap mappedGraph = MDGraphInitSMD.initMDGraphAndMap();
        MDGraph mdGraph = mappedGraph.getGraph();
        MappedMDGraph mdMap = mappedGraph.getMap();
        MDData data = mappedGraph.getData();

        Set<List<String>> factsAndCoordinates = MDGraphUtils.getBaseSetOfAggregation(
                data, mdGraph,
                List.of(director, country),
                boxOffice);

        PrintUtils.printSetOfLists(factsAndCoordinates);
    }
}
