package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;

public class LevelMember extends MDElement implements Comparable {
    public LevelMember(String uri) {
        super(uri);
    }

    public static LevelMember unknown() {
        return new LevelMember(ConstantOrUnknown.unknown.getUri());
    }

    @Override
    public int compareTo(Object o) {
        return this.getUri().compareTo(((LevelMember) o).getUri());
    }
}
