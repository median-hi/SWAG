package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;

public class Level extends MDElement {
    public Level(String uri) {
        super(uri);
    }

    public static Level unknown() {
        return new Level(ConstantOrUnknown.unknown.getUri());
    }
}
