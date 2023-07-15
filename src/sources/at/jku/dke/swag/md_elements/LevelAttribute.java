package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;

public class LevelAttribute extends MDElement {
    public LevelAttribute(String uri) {
        super(uri);
    }

    public static LevelAttribute unknown() {
        return new LevelAttribute(ConstantOrUnknown.unknown.getUri());
    }
}
