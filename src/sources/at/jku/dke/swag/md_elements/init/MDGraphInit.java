package at.jku.dke.swag.md_elements.init;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.basic_elements.Constant;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.md_elements.*;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class MDGraphInit {

    public static MDGraph initMDGraph() {
        MDGraph mdGraph = new MDGraph();

        Fact fact = new Fact("migrAsylumApps");
        mdGraph.setF(Set.of(fact));

        Dimension destinationDim = new Dimension("destinationDim");
        Hierarchy geoHierarchy = new Hierarchy("geoHierarchy");
        Hierarchy govHierarchy = new Hierarchy("govHierarchy");

        Level geo = new Level("geo");
        Level continent = new Level("continent");
        Level govType = new Level("govType");
        Level destinationTop = new Level("destinationTop");

        Dimension citizenshipDim = new Dimension("citizenshipDim");
        Hierarchy citizenshipGeoHierarchy = new Hierarchy("citizenshipGeoHierarchy");
        Hierarchy citizenshipGovHierarchy = new Hierarchy("citizenshipGovHierarchy");

        Level citizen = new Level("citizen");
        Level citizenshipTop = new Level("citizenshipTop");

        Dimension timeDim = new Dimension("timeDim");
        Hierarchy timeHierarchy = new Hierarchy("timeHierarchy");

        Level refPeriod = new Level("refPeriod");
        Level year = new Level("year");
        Level timeTop = new Level("timeTop");

        Measure numOfApps = new Measure("numOfApps");

        LevelMember uk = new LevelMember("UK");
        LevelMember germany = new LevelMember("DE");
        LevelMember austria = new LevelMember("AT");

        LevelMember asia = new LevelMember("AS");
        LevelMember europe = new LevelMember("EU");

        LevelMember all_destinationDim = new LevelMember("all_destinationDim");
        LevelMember all_citizenshipDim = new LevelMember("all_citizenshipDim");
        LevelMember all_timeDim = new LevelMember("all_timeDim");


        mdGraph.getL().addAll(Set.of(geo, continent, govType, destinationTop, refPeriod, year, timeTop));
        mdGraph.getD().addAll(Set.of(destinationDim, timeDim, citizenshipDim));
        mdGraph.getH().addAll(Set.of(geoHierarchy, govHierarchy, timeHierarchy, citizenshipGeoHierarchy, citizenshipGovHierarchy));
        mdGraph.getHL().put(geoHierarchy, Set.of(geo, continent, destinationTop));
        mdGraph.getHL().put(govHierarchy, Set.of(geo, govType, destinationTop));
        mdGraph.getHL().put(citizenshipGeoHierarchy, Set.of(citizen, continent, citizenshipTop));
        mdGraph.getHL().put(citizenshipGovHierarchy, Set.of(citizen, govType, citizenshipTop));
        mdGraph.getHL().put(timeHierarchy, Set.of(refPeriod, year, timeTop));
        mdGraph.getDH().put(destinationDim, Set.of(geoHierarchy, govHierarchy));
        mdGraph.getDH().put(citizenshipDim, Set.of(citizenshipGeoHierarchy, citizenshipGovHierarchy));
        mdGraph.getDH().put(timeDim, Set.of(timeHierarchy));
        mdGraph.getLL().add(new RollUpPair(geo, continent));
        mdGraph.getLL().add(new RollUpPair(geo, govType));
        mdGraph.getLL().add(new RollUpPair(continent, destinationTop));
        mdGraph.getLL().add(new RollUpPair(govType, destinationTop));
        mdGraph.getLL().add(new RollUpPair(citizen, continent));
        mdGraph.getLL().add(new RollUpPair(citizen, govType));
        mdGraph.getLL().add(new RollUpPair(continent, citizenshipTop));
        mdGraph.getLL().add(new RollUpPair(govType, citizenshipTop));
        mdGraph.getLL().add(new RollUpPair(refPeriod, year));
        mdGraph.getLL().add(new RollUpPair(year, timeTop));

        BiFunction<Set<RollUpPair>, Set<RollUpPair>, Set<RollUpPair>> f = (x, y) -> {
            Set<RollUpPair> z = new HashSet<>(x);
            z.addAll(y);
            return z;
        };

        mdGraph.getHLL().merge(geoHierarchy, Set.of(new RollUpPair(geo, continent)), f);
        mdGraph.getHLL().merge(geoHierarchy, Set.of(new RollUpPair(continent, destinationTop)), f);
        mdGraph.getHLL().merge(govHierarchy, Set.of(new RollUpPair(geo, govType)), f);
        mdGraph.getHLL().merge(govHierarchy, Set.of(new RollUpPair(govType, destinationTop)), f);
        mdGraph.getHLL().merge(citizenshipGeoHierarchy, Set.of(new RollUpPair(citizen, continent)), f);
        mdGraph.getHLL().merge(citizenshipGovHierarchy, Set.of(new RollUpPair(citizen, govType)), f);
        mdGraph.getHLL().merge(citizenshipGovHierarchy, Set.of(new RollUpPair(continent, citizenshipTop)), f);
        mdGraph.getHLL().merge(citizenshipGovHierarchy, Set.of(new RollUpPair(govType, citizenshipTop)), f);
        mdGraph.getHLL().merge(timeHierarchy, Set.of(new RollUpPair(refPeriod, year)), f);
        mdGraph.getHLL().merge(timeHierarchy, Set.of(new RollUpPair(year, timeTop)), f);

        mdGraph.getM().add(numOfApps);
        mdGraph.getMembers().put(geo, new TreeSet<>(Set.of(germany, austria, uk)));
        mdGraph.getMembers().put(continent, new TreeSet<>(Set.of(asia, europe)));
        mdGraph.getMembers().put(destinationTop, new TreeSet<>(Set.of(all_destinationDim)));
        mdGraph.getMembers().put(timeTop, new TreeSet<>(Set.of(all_timeDim)));
        mdGraph.getMembers().put(citizenshipTop, new TreeSet<>(Set.of(all_citizenshipDim)));

        mdGraph.getP().add(AppConstants.D_PRED);
        mdGraph.getP().add(AppConstants.D_PRED_1);
        mdGraph.getP().add(AppConstants.D_PRED_2);
        mdGraph.getP().add(AppConstants.GRAN_PARAM);
        mdGraph.getP().add(AppConstants.GRAN_PARAM_1);
        mdGraph.getP().add(AppConstants.CONTINENT_NODE);
        mdGraph.getP().add(AppConstants.GEO_NODE);
        mdGraph.getP().add(AppConstants.GEO_NODE_1);
        mdGraph.getP().add(AppConstants.M_PRED);
        mdGraph.getP().add(AppConstants.DICE_PARAM);
        mdGraph.getP().add(AppConstants.DICE_PARAM_1);

        Set<Constant> timePreds = Set.of(AppConstants.YEAR_AFTER_2010,
                AppConstants.YEAR_AFTER_2013,
                AppConstants.YEAR_AFTER_2015);

        Set<Constant> geos = Set.of(AppConstants.AUSTRIA, AppConstants.GERMANY, AppConstants.UK);

        Set<Constant> consts = new HashSet<>();
        consts.addAll(mdGraph.getL());
        consts.addAll(mdGraph.getD());
        consts.addAll(mdGraph.getM());
        consts.addAll(mdGraph.getF());
        consts.addAll(mdGraph.getH());
        consts.addAll(mdGraph.getMembers()
                .values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet()));
        consts.addAll(timePreds);
        consts.addAll(geos);
        consts.add(AppConstants.INTENSITY_GT_30K);

        mdGraph.setC(consts);

        mdGraph.getDOM().put(AppConstants.D_PRED, timePreds);
        mdGraph.getDOM().put(AppConstants.D_PRED_1, timePreds);
        mdGraph.getDOM().put(AppConstants.D_PRED_2, timePreds);
        mdGraph.getDOM().put(AppConstants.M_PRED, Set.of(AppConstants.INTENSITY_GT_30K));

        mdGraph.getDOM().put(AppConstants.GRAN_PARAM,
                mdGraph.getLevelsOfDimension(AppConstants.DESTINATION_DIM)
                        .stream()
                        .map(l -> (Constant) l)
                        .collect(Collectors.toSet()));
        mdGraph.getDOM().put(AppConstants.GRAN_PARAM_1,
                mdGraph.getLevelsOfDimension(AppConstants.DESTINATION_DIM)
                        .stream()
                        .map(l -> (Constant) l)
                        .collect(Collectors.toSet()));

        mdGraph.getDOM().put(AppConstants.CONTINENT_NODE, Set.of(asia, europe));

        mdGraph.getDOM().put(AppConstants.GEO_NODE, geos);
        mdGraph.getDOM().put(AppConstants.GEO_NODE_1, geos);
        mdGraph.getDOM().put(AppConstants.DICE_PARAM, geos);
        mdGraph.getDOM().put(AppConstants.DICE_PARAM_1, geos);

        return mdGraph;
    }

    public Set<Parameter> initParameters() {
        Set<Parameter> parameters = new HashSet<>();
        parameters.add(new Parameter("dPred"));
        parameters.add(new Parameter("mPred"));
        parameters.add(new Parameter("continentNode"));
        return parameters;
    }
}
