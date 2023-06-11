package at.jku.dke.swag.sparql;

import at.jku.dke.swag.md_elements.*;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MDQuerySparqlGenerator {

    private MDGraph mdGraph;
    private MappedMDGraph mappedMDGraph;

    public MDQuerySparqlGenerator(MDGraph mdGraph, MappedMDGraph mappedMDGraph) {
        this.mdGraph = mdGraph;
        this.mappedMDGraph = mappedMDGraph;
    }

    public static Element joinQueryPatterns(Element e1, Element e2) {

        if (e1 == null && e2 == null)
            return null;
        if (e1 == null && e2 != null)
            return e2;
        if (e1 != null && e2 == null)
            return e1;
        if (e2 instanceof Element && e1 instanceof Element) {
            ElementGroup block2 = new ElementGroup();
            block2.addElement(e1);
            block2.addElement(e2);
            return block2;
        }
        return null;
    }

    public static Query querifyString(String queryString){
        return QueryFactory.create(queryString);
    }


    public String makeQuery(List<MDElement> path){
        StringBuilder builder = new StringBuilder();

        String factQuery = "{" + mappedMDGraph.get(mdGraph.getF().stream().findFirst().orElseThrow()) + "}";
        builder.append(factQuery);

        MDElement prevElm = mdGraph.getF().stream().findFirst().orElseThrow();
        for (MDElement elm : path){
            if (!elm.equals(mdGraph.getF().stream().findFirst().orElseThrow())){
                String tmpLink = mappedMDGraph.get(prevElm, elm);
                String tmp = mappedMDGraph.get(elm);
                builder.append("{" + tmpLink + "}");
                builder.append("{" + tmp + "}");
                prevElm = elm;
            }
        }
        return "{SELECT ?" + path.get(0).getUri() + " ?" + path.get(path.size()-1) + " WHERE{" + builder.toString() + "}}";
    }

    public String makeGroupByQuery(List<Level> groupBy){

        String finalQuery = "";


        for (Level level : groupBy){
            String q = makeQuery(MDGraphUtils.makePath(mdGraph, level));
            finalQuery = finalQuery + "\n" + q;
        }

        finalQuery = "SELECT %s WHERE{" + finalQuery + "} GROUP BY %s";
        finalQuery = String.format(finalQuery, getSelectString(groupBy), getSelectString(groupBy));

        return finalQuery;
    }

    public String makeAggregationQuery(Fact fact, Measure m, List<Level> groupBy){

            String finalQuery = "{" + makeMsrQuery(fact, m) + "}" + "{" + makeDimensionsQuery(fact, groupBy) + "}" ;

            finalQuery = "SELECT %s %s WHERE{" + finalQuery + "} GROUP BY %s";
            finalQuery = String.format(finalQuery, String.format("(SUM(%s) AS ?sum) ", getElmVarName(m)),
                    getSelectString(groupBy), getSelectString(groupBy));

            return finalQuery;
    }

    public String makeDimensionsQuery(Fact fact, List<Level> groupBy){

        String finalQuery = "";

        for (Level level : groupBy){
            String q = makeQuery(MDGraphUtils.makePath(mdGraph, level));
            finalQuery = finalQuery + "\n" + q;
        }

        finalQuery = "SELECT DISTINCT %s WHERE{" + finalQuery + "}";
        finalQuery = String.format(finalQuery, getSelectStringOfDimensionQuery(fact, groupBy));


        return finalQuery;
    }

    public String makeMsrQuery(Fact fact, Measure m){

        String finalQuery = "{" + mappedMDGraph.get(fact) + "} \n";
        finalQuery += "{" + mappedMDGraph.get(fact, m) + "}";

        finalQuery = "SELECT DISTINCT %s WHERE{" + finalQuery + "}";
        finalQuery = String.format(finalQuery, getSelectStringOfMeasureQuery(fact, m));

        return finalQuery;
    }

    private String getSelectString(List<Level> elms){
        String res = "";

        for (Level elm: elms){
            res += getElmVarName(elm) + " ";
        }
        return res;
    }

    private String getSelectStringOfDimensionQuery(Fact fact, List<Level> elms){
        String res = "";

        res += getElmVarName(fact) + " ";

        for (Level elm: elms){
            res += getElmVarName(elm) + " ";
        }
        return res;
    }

    private String getSelectStringOfMeasureQuery(Fact fact, Measure m){
        String res = "";
        res += getElmVarName(fact) + " ";
        res += getElmVarName(m) + " ";
        return res;
    }

    private String getElmVarName(MDElement elm){
        return "?" + elm.getUri().substring(elm.getUri().lastIndexOf("/") +1, elm.getUri().length());
    }

}
