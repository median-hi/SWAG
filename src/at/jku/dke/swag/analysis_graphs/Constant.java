package at.jku.dke.swag.analysis_graphs;

public class Constant extends ElementWithUri implements PairOrConstant{

    public Constant(String uri) {
        super(uri);
    }

    public boolean isConstant(){
        return true;
    }

    public boolean isPair(){
        return false;
    }

    public static boolean isUnknown(Constant constant){
        return constant.getUri().equals(unknown.getUri());
    }

    public static final Constant unknown = new Constant("unknown");

    public Constant copy(){
        return this;
    }
}
