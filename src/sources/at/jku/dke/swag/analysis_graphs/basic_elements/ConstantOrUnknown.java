package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.ElementWithUri;

public class ConstantOrUnknown extends ElementWithUri implements PairOrConstant {

    public static final ConstantOrUnknown unknown = new ConstantOrUnknown("unknown");

    public ConstantOrUnknown(String uri) {
        super(uri);
    }

    public static boolean isUnknown(ConstantOrUnknown constantOrUnknown) {
        return constantOrUnknown.getUri().equals(unknown.getUri());
    }

    @Override
    public boolean isConstant() {
        return !isUnknown();
    }

    @Override
    public boolean isUnknown() {
        return isUnknown(this);
    }

    public boolean isConstantOrUnknown() {
        return true;
    }

    public boolean isPair() {
        return false;
    }

    public ConstantOrUnknown copy() {
        return this;
    }
}
