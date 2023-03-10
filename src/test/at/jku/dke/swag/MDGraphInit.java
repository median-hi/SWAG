package at.jku.dke.swag;

import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.md_elements.*;

import java.util.HashSet;
import java.util.Set;

public class MDGraphInit {


    public static MDGraphAndMap initMDGraphAndMap(){
        MDGraph mdGraph = new MDGraph();

        Fact fact = new Fact("film");
        mdGraph.setF(Set.of(fact));

        Dimension directiontDim = new Dimension("directionDim");
        Hierarchy directionHier = new Hierarchy("directionHier");
        Level director = new Level("director");
        Level gender = new Level("gender");

        Dimension contenttDim = new Dimension("contentDim");
        Hierarchy contentHier = new Hierarchy("contentHier");
        Level genre = new Level("genre");

        Dimension origintDim = new Dimension("origintDim");
        Hierarchy originHier = new Hierarchy("originHier");
        Level country = new Level("country");
        Level continent = new Level("continent");

        Dimension timeDim = new Dimension("timeDim");
        Hierarchy timeHierarchy = new Hierarchy("timeHierarchy");
        Level date = new Level("date");
        Level year = new Level("year");

        Measure boxOffice = new Measure("boxOffice");

        mdGraph.getL().addAll(Set.of(country, continent, director, gender, date, year, genre));
        mdGraph.getD().addAll(Set.of(directiontDim, timeDim, origintDim, contenttDim));
        mdGraph.getH().addAll(Set.of(directionHier, contentHier, originHier, timeHierarchy));
        mdGraph.getHL().put(directionHier, Set.of(director, gender));
        mdGraph.getHL().put(contentHier, Set.of(genre));
        mdGraph.getHL().put(originHier, Set.of(country, continent));
        mdGraph.getHL().put(timeHierarchy, Set.of(date, year));

        mdGraph.getDH().put(directiontDim, Set.of(directionHier));
        mdGraph.getDH().put(contenttDim, Set.of(contentHier));
        mdGraph.getDH().put(timeDim, Set.of(timeHierarchy));
        mdGraph.getDH().put(origintDim, Set.of(originHier));

        mdGraph.getLL().add(new RollUpPair(country, continent));
        mdGraph.getLL().add(new RollUpPair(director, gender));
        mdGraph.getLL().add(new RollUpPair(date, year));

        mdGraph.getHLL().put(originHier, Set.of(new RollUpPair(country, continent)));
        mdGraph.getHLL().put(directionHier, Set.of(new RollUpPair(director, gender)));
        mdGraph.getHLL().put(timeHierarchy, Set.of(new RollUpPair(date, year)));

        mdGraph.getM().add(boxOffice);

        MappedMDGraph graph = new MappedMDGraph();

        graph.add(fact, "SELECT  ?film\n" +
                "where {\n" +
                "?film <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q11424>. ?film <http://www.wikidata.org/prop/direct/P2142> ?boxOffice1.\n" +
                "}");
        graph.add(fact, boxOffice, "SELECT ?film ?boxOoffice WHERE {\n" +
                "?film <http://www.wikidata.org/prop/P2142> ?boxOfficeNode. ?boxOfficeNode <http://www.wikidata.org/prop/statement/value/P2142> ?value.\n" +
                "?value <http://wikiba.se/ontology#quantityAmount> ?boxOffice. ?value <http://wikiba.se/ontology#quantityUnit> <http://www.wikidata.org/entity/Q4917>.\n" +
                "?boxOfficeNode <http://www.wikidata.org/prop/qualifier/P3005> <http://www.wikidata.org/entity/Q13780930>.}");

        graph.add(director, "select ?director where {\n" +
                "?director <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q5>.\n" +
                "}");
        graph.add(gender, "select ?gender\n" +
                "where {\n" +
                "?gender <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q48264>.\n" +
                "}");
        graph.add(country, "SELECT  ?country\n" +
                "where {?country <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q6256>}");
        graph.add(continent, "select ?continent\n" +
                "where {" +
                " ?continent <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q5107> .}");
        graph.add(genre, "Select ?genre\n" +
                "where{\n" +
                "?genre <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q201658> \n" +
                "}");
        graph.add(date, "SELECT ?date where\n" +
                "{   \n" +
                " {SELECT ?film1 (MIN (?pubDate1) AS ?date) WHERE {?film1 <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q11424>.\n" +
                "?film1 <http://www.wikidata.org/prop/direct/P2142> ?boxOffice3. ?film1 <http://www.wikidata.org/prop/direct/P577> ?pubDate1. } GROUP BY ?film1 } \n" +
                "}");
        graph.add(year, "select ?year where{\n" +
                "?year <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q3186692> \n" +
                "}");

        graph.add(fact, director, "select ?film ?director where {\n" +
                "?film <http://www.wikidata.org/prop/direct/P57> ?director.\n" +
                "}");
        graph.add(fact, genre, "SELECT ?film ?genre where\n" +
                "{\n" +
                "    ?film <http://www.wikidata.org/prop/direct/P136> ?genre.\n" +
                "}");
        graph.add(fact, date, "SELECT ?date where\n" +
                "{   \n" +
                " {SELECT ?film1 (MIN (?pubDate1) AS ?date) WHERE {?film1 <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q11424>.\n" +
                "?film1 <http://www.wikidata.org/prop/direct/P2142> ?boxOffice3. ?film1 <http://www.wikidata.org/prop/direct/P577> ?pubDate1. } GROUP BY ?film1 } \n" +
                "}");
        graph.add(fact, country, "SELECT  ?country where\n" +
                "{?country <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q6256>}");

        graph.add(director, gender, "select ?director ?gender\n" +
                "where{\n" +
                "?director <http://www.wikidata.org/prop/direct/P21> ?gender.\n" +
                "}");
        graph.add(country, continent, "select ?country ?continent where\n" +
                "{\n" +
                "?country <http://www.wikidata.org/prop/direct/P30> ?continent.\n" +
                "}");
        graph.add(date, year, "select ?date ?year\n" +
                "where{      \n" +
                "      ?dateUri  <http://www.wikidata.org/prop/direct/P585> ?date. \n" +
                "      ?dateUri <http://www.wikidata.org/prop/direct/P31>  <http://www.wikidata.org/entity/Q47150325> . \n" +
                "      ?dateUri <http://www.wikidata.org/prop/direct/P361> ?month. \n" +
                "      ?month <http://www.wikidata.org/prop/direct/P361> ?year. \n" +
                "}");

        return new MDGraphAndMap(mdGraph, graph);
    }

}
