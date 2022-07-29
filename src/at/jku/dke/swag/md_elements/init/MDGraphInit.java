package at.jku.dke.swag.md_elements.init;

import at.jku.dke.swag.md_elements.*;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;

import java.util.HashSet;
import java.util.Set;

public class MDGraphInit {

    public static MDGraph initMDGraph(){
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

        LevelMember germany = new LevelMember("DE");
        LevelMember all_destinationDim = new LevelMember("all_destinationDim");
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
        mdGraph.getM().add(numOfApps);
        mdGraph.getMembers().put(geo, Set.of(germany));
        mdGraph.getMembers().put(destinationTop, Set.of(all_destinationDim));
        mdGraph.getMembers().put(timeTop, Set.of(all_timeDim));

        return mdGraph;
    }

    public Set<Parameter> initParameters(){
        Set<Parameter> parameters = new HashSet<>();
        parameters.add(new Parameter("dPred"));
        parameters.add(new Parameter("mPred"));
        parameters.add(new Parameter("continentNode"));
        return parameters;
    }
}
