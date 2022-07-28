package at.jku.dke.swag.analysis_graphs.basic_elements;

public class Constant extends ConstantOrUnknown{
    public Constant(String uri) {
        super(uri);
    }

    @Override
    public boolean isStrictlyConstant() {
        return true;
    }

    @Override
    public boolean isStrictlyUnknown() {
        return false;
    }

}
