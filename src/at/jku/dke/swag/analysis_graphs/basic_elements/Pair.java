package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.Copiable;

public class Pair implements PairOrConstant, Copiable {

    private Parameter parameter;

    private ConstantOrUnknown constantOrUnknown;

    public Pair(Parameter parameter, ConstantOrUnknown contant) {
        this.parameter = parameter;
        this.constantOrUnknown = contant;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public ConstantOrUnknown getConstant() {
        return constantOrUnknown;
    }

    public void setConstant(ConstantOrUnknown constantOrUnknown) {
        this.constantOrUnknown = constantOrUnknown;
    }

    @Override
    public boolean isConstantOrUnknown() {
        return false;
    }

    @Override
    public boolean isPair() {
        return true;
    }

    @Override
    public boolean isStrictlyUnknown() {
        return false;
    }

    @Override
    public boolean isStrictlyConstant() {
        return false;
    }


    public ConstantOrUnknown getConstantOrUnknown() {
        return constantOrUnknown;
    }

    public void setConstantOrUnknown(ConstantOrUnknown constantOrUnknown) {
        this.constantOrUnknown = constantOrUnknown;
    }


    @Override
    public Pair copy() {
        return new Pair(this.getParameter(), this.getConstant());
    }
}
