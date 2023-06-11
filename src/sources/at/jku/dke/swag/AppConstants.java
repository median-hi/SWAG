package at.jku.dke.swag;

import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;

public class AppConstants {

    public static final Dimension DESTINATION_DIM = new Dimension("destinationDim");
    public static final Level CONTINENT = new Level("continent");
    public static final Level GEO = new Level("geo");

    public static final Dimension TIME_DIM = new Dimension("timeDim");

    public static final Dimension CITIZENSHIP_DIM = new Dimension("citizenshipDim");

    public static final Dimension CITIZEN = new Dimension("citizen");

    public static final Level YEAR = new Level("year");

    public static final Level REF_PERIOD = new Level("refPeriod");
    public static final Parameter CONTINENT_NODE = new Parameter("continentNode");

    public static final Parameter GEO_NODE = new Parameter("geoNode");

    public static final Parameter GEO_NODE_1 = new Parameter("geoNode1");

    public static final Parameter D_PRED = new Parameter("dPred");

    public static final Parameter M_PRED = new Parameter("mPred");

}
