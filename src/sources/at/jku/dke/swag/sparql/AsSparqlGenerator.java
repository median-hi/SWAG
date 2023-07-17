package at.jku.dke.swag.sparql;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.Constant;
import at.jku.dke.swag.analysis_graphs.basic_elements.PairOrConstant;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.*;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Node_Variable;
import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.syntax.TripleCollectorBGP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AsSparqlGenerator {

    private final MDGraph md;
    private final AnalysisSituation as;
    private final MdUtils utils;

    public AsSparqlGenerator(MDGraph md, AnalysisSituation as) {
        this.md = md;
        this.as = as;
        utils = this.new MdUtils();
    }


    public MDGraph getMd() {
        return md;
    }

    public AnalysisSituation getAs() {
        return as;
    }

    public String generateQuery() {

        TripleCollectorBGP bgp = new TripleCollectorBGP();
        List<String> conds = new ArrayList<>();
        List<String> filters = new ArrayList<>();
        List<String> aggs = new ArrayList<>();
        List<Var> grans = new ArrayList<Var>();

        SetBasedBGP.addBgpToBgp(bgp, utils.getTriplesOfFactClass());

        for (Map.Entry<Dimension, PairOrConstant> entry : as.getGranularities().entrySet()) {
            Level l = (Level) Utils.actual(entry.getValue());
            if (!l.equals(getMd().top(entry.getKey()))) {
                SetBasedBGP.addBgpToBgp(bgp, getUtils().getTriplesOfLevel(entry.getKey(), l));
                grans.add(Var.alloc(getUtils().getVarOfLevel(entry.getKey(), l)));
            }
        }

        for (Map.Entry<Dimension, PairOrConstant> entry : as.getDiceLevels().entrySet()) {
            Level l = (Level) Utils.actual(entry.getValue());
            if (!l.equals(getMd().top(entry.getKey()))) {
                SetBasedBGP.addBgpToBgp(bgp, getUtils().getTriplesOfLevel(entry.getKey(), l));
                LevelMember m = (LevelMember) Utils.actual(as.getDiceNodes().get(entry.getKey()));
                conds.add(getUtils().getDiceNodeExpression(entry.getKey(), l, m));
            }
        }

        for (Map.Entry<Dimension, BindableSet> entry : as.getDimensionSelection().entrySet()) {
            for (PairOrConstant pc : entry.getValue().getElements()) {
                Constant c = (Constant) Utils.actual(pc);
                MDElement elm = getMd().getMdElemes().get(c);

                if (elm instanceof Level) {
                    SetBasedBGP.addBgpToBgp(bgp, getUtils().getTriplesOfLevel(entry.getKey(), (Level) elm));
                    conds.add(getUtils().getSelectionExpression(entry.getKey(), c));
                } else {
                    if (elm instanceof LevelAttribute) {
                        SetBasedBGP.addBgpToBgp(
                                bgp,
                                getUtils()
                                        .getTriplesOfLevelAttribute(
                                                entry.getKey(),
                                                getMd()
                                                        .getFirstLevelOfAttribute(
                                                                entry.getKey(),
                                                                (LevelAttribute) elm),
                                                (LevelAttribute) elm)
                        );
                        conds.add(getUtils().getSelectionExpression(entry.getKey(), c));
                    }
                }
            }
        }

        for (PairOrConstant pc : as.getMeasures().getElements()) {
            Constant c = (Constant) Utils.actual(pc);
            MDElement elm = getMd().getMdElemes().get(c);
            SetBasedBGP.addBgpToBgp(bgp, getUtils().getTriplesOfMeasure((Measure) elm));
            aggs.add(getUtils().getResultMeasureExpression(c));
        }

        for (PairOrConstant pc : as.getResultFilters().getElements()) {
            Constant c = (Constant) Utils.actual(pc);
            MDElement elm = getMd().getMdElemes().get(c);
            SetBasedBGP.addBgpToBgp(bgp, getUtils().getTriplesOfMeasure((Measure) elm));
            filters.add(getUtils().getFilterExpression(c));
        }

        String query = "";
        query += "SELECT ";

        for (String agg : aggs) {
            query += "(" + agg + ")";
        }

        for (Var gran : grans) {
            query += " ?" + gran.getName() + " ";
        }

        query += " WHERE {";
        for (Triple t : bgp.getBGP().getList()) {
            query += "?" + ((Node_Variable) t.getSubject()).getName() +
                    " <" + t.getPredicate().getURI() + "> " +
                    ((t.getObject() instanceof Node_Variable) ?
                            "?" + ((Node_Variable) t.getObject()).getName() :
                            " <" + t.getObject().getURI() + "> ") +
                    ". ";
        }

        query += " \n ";
        for (String cond : conds) {
            query += cond + " \n ";
        }
        query += " } GROUP BY ";

        for (Var gran : grans) {
            query += " ?" + gran.getName() + " ";
        }

        query += " HAVING (";

        for (String filter : filters) {
            query += filter + " && ";
        }

        query += ")";

        System.out.println("query: " + query);
        //System.out.println("query: " + (QueryFactory.create(query)).toString());

        return "";
    }


    public MdUtils getUtils() {
        return utils;
    }


    public class MdUtils {

        public String getVarOfLevel(Dimension i, Level l) {
            return i.getUri().substring(0, 2) + "_" + l.getUri();
        }

        public String getVarOfLevelAttribute(Dimension i, Level l, LevelAttribute a) {
            return i.getUri().substring(0, 2) + "_" + l.getUri().substring(0, 2) + "_" + a.getUri();
        }

        public String getVarOfFactClass() {
            return getMd().getFact().getUri();
        }

        public String getVarOfMeasure(Measure m) {
            return m.getUri();
        }

        public MDElement getMdElements(Constant c) {
            return getMd().getMdElemes().get(c);
        }

        public MDElement getBaseMeasures(Constant m) {
            return getMd().getMdElemes().get(m);
        }

        public String getFilterExpression(Constant c) {
            String var = "";
            MDElement elm = getBaseMeasures(c);
            return getMd().getExpressions().get(c).replace(":1", getVarOfMeasure((Measure) elm));
        }

        public String getResultMeasureExpression(Constant c) {
            String var = "";
            MDElement elm = getMdElements(c);
            return getMd().getExpressions().get(c).replace(":1", getVarOfMeasure((Measure) elm));
        }

        public String getSelectionExpression(Dimension i, Constant c) {
            String var = "";
            MDElement elm = getMdElements(c);
            if (elm instanceof Level) {
                return wrapInFilter(
                        getMd()
                                .getExpressions()
                                .get(c)
                                .replace(":1", getVarOfLevel(i, (Level) elm))
                );
            } else {
                if (elm instanceof LevelAttribute) {
                    LevelAttribute attr = (LevelAttribute) elm;
                    return wrapInFilter(
                            getMd()
                                    .getExpressions()
                                    .get(c)
                                    .replace(":1",
                                            getVarOfLevelAttribute(i, getMd().getFirstLevelOfAttribute(i, attr),
                                                    attr)
                                    )
                    );
                }
            }
            return null;
        }

        public String wrapInFilter(String str) {
            return String.format("FILTER(%s)", str);
        }

        public String getDiceNodeExpression(Dimension i, Level l, LevelMember m) {
            return wrapInFilter(String.format("%s = <%s>", getVarOfLevel(i, l), m.getUri()));
        }

        public TripleCollectorBGP getTriplesOfLevel(Dimension i, Level l) {

            TripleCollectorBGP triplePattern = new TripleCollectorBGP();

            Triple ta = new Triple(
                    NodeFactory.createVariable(getVarOfFactClass()),
                    NodeFactory.createURI(getMd().bot(i).getUri()),
                    NodeFactory.createVariable(getVarOfLevel(i, getMd().bot(i)))
            );
            Triple tb = new Triple(
                    NodeFactory.createVariable(getVarOfLevel(i, getMd().bot(i))),
                    NodeFactory.createURI("http://purl.org/qb4olap/cubes#memberOf"),
                    NodeFactory.createURI(getMd().bot(i).getUri())
            );

            triplePattern.addTriple(ta);
            triplePattern.addTriple(tb);

            for (RollUpPair pair : MDGraphUtils.getRollUpPath(getMd(), i, getMd().bot(i), l)) {
                Triple t1 = new Triple(
                        NodeFactory.createVariable(getVarOfLevel(i, pair.getFrom())),
                        NodeFactory.createURI(MDGraphUtils.getRollUpProperty(getMd(), i, pair)),
                        NodeFactory.createVariable(getVarOfLevel(i, pair.getTo()))
                );
                Triple t2 = new Triple(
                        NodeFactory.createVariable(getVarOfLevel(i, pair.getTo())),
                        NodeFactory.createURI("http://purl.org/qb4olap/cubes#memberOf"),
                        NodeFactory.createURI(pair.getTo().getUri())
                );

                triplePattern.addTriple(t1);
                triplePattern.addTriple(t2);
            }

            return triplePattern;
        }

        public TripleCollectorBGP getTriplesOfLevelAttribute(Dimension i, Level l, LevelAttribute a) {
            TripleCollectorBGP triplePattern = getTriplesOfLevel(i, l);
            triplePattern.addTriple(
                    new Triple(
                            NodeFactory.createVariable(getVarOfLevel(i, l)),
                            NodeFactory.createURI(a.getUri()),
                            NodeFactory.createVariable(getVarOfLevelAttribute(i, l, a)))
            );
            return triplePattern;
        }

        public TripleCollectorBGP getTriplesOfMeasure(Measure m) {
            TripleCollectorBGP triplePattern = new TripleCollectorBGP();
            triplePattern.addTriple(
                    new Triple(
                            NodeFactory.createVariable(getVarOfFactClass()),
                            NodeFactory.createURI(m.getUri()),
                            NodeFactory.createVariable(getVarOfMeasure(m))
                    )
            );
            return triplePattern;
        }


        public TripleCollectorBGP getTriplesOfFactClass() {
            TripleCollectorBGP triplePattern = new TripleCollectorBGP();
            triplePattern.addTriple(
                    new Triple(
                            NodeFactory.createVariable(getVarOfFactClass()),
                            NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                            NodeFactory.createURI("http://purl.org/linked-data/cube#Observation")
                    )
            );
            return triplePattern;
        }
    }
}
