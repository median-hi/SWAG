package at.jku.dke.swag;

import at.jku.dke.swag.analysis_graphs.basic_elements.Constant;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Hierarchy;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;

public class AppConstants {

    public static final Dimension DESTINATION_DIM = new Dimension("destinationDim");
    public static final Hierarchy GEO_HIER = new Hierarchy("geoHierarchy");
    public static final Level CONTINENT = new Level("continent");

    public static final LevelMember UK = new LevelMember("UK");
    public static final LevelMember GERMANY = new LevelMember("DE");
    public static final LevelMember AUSTRIA = new LevelMember("AT");

    public static final LevelMember FAKE = new LevelMember("FAKE");

    public static final Level TOP_DESTINATION_DIM = new Level("destinationTop");

    public static final Parameter GRAN_PARAM = new Parameter("granParam");

    public static final Parameter GRAN_PARAM_1 = new Parameter("granParam1");

    public static final Parameter DICE_PARAM = new Parameter("diceParam");

    public static final Parameter DICE_PARAM_1 = new Parameter("diceParam1");

    public static final Level GEO = new Level("geo");

    public static final Dimension TIME_DIM = new Dimension("timeDim");

    public static final Dimension CITIZENSHIP_DIM = new Dimension("citizenshipDim");

    public static final Level CITIZEN = new Level("citizen");

    public static final Level YEAR = new Level("year");

    public static final Level REF_PERIOD = new Level("refPeriod");
    public static final Parameter CONTINENT_NODE = new Parameter("continentNode");

    public static final Parameter GEO_NODE = new Parameter("geoNode");

    public static final Parameter GEO_NODE_1 = new Parameter("geoNode1");

    public static final Parameter D_PRED = new Parameter("dPred");

    public static final Constant YEAR_AFTER_2015 = new Constant("yearAfter2015");

    public static final Constant YEAR_AFTER_2013 = new Constant("yearAfter2013");

    public static final Parameter D_PRED_1 = new Parameter("dPred1");

    public static final Parameter D_PRED_2 = new Parameter("dPred2");

    public static final Parameter M_PRED = new Parameter("mPred");

}
