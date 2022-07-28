package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.ElementWithUri;

public class ConstantOrUnknown extends ElementWithUri implements PairOrConstant{



    public ConstantOrUnknown(String uri) {
        super(uri);
    }

    @Override
    public boolean isStrictlyConstant() {
        return false;
    }

    @Override
    public boolean isStrictlyUnknown() {
        return isUnknown(this);
    }

    public boolean isConstantOrUnknown(){
        return true;
    }

    public boolean isPair(){
        return false;
    }

    public static boolean isUnknown(ConstantOrUnknown constantOrUnknown){
        return constantOrUnknown.getUri().equals(unknown.getUri());
    }

    public static final ConstantOrUnknown unknown = new ConstantOrUnknown("unknown");

    public ConstantOrUnknown copy(){
        return this;
    }
}
