package at.jku.dke.swag.analysis_graphs;

public class Pair implements PairOrConstant, Copiable{

    private Parameter parameter;
    private Constant constant;

    public Pair(Parameter parameter, Constant contant) {
        this.parameter = parameter;
        this.constant = contant;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Constant getConstant() {
        return constant;
    }

    public void setConstant(Constant constant) {
        this.constant = constant;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public boolean isPair() {
        return true;
    }

    @Override
    public Pair copy() {
        return new Pair(this.getParameter(), this.getConstant());
    }
}
