package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.Copiable;
import at.jku.dke.swag.analysis_graphs.asm_elements.LocationValue;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;

import java.util.Objects;

public class Pair implements PairOrConstant, Copiable, Comparable {

    MDGraph graph = MDGraphInit.initMDGraph();
    private Parameter parameter;

    private ConstantOrUnknown constantOrUnknown;

    public Pair(Parameter parameter, ConstantOrUnknown constant) {
        if (constant.isUnknown() ||
                (constant.isConstant() && graph.getDOM().get(parameter).contains((Constant) constant))) {
            this.parameter = parameter;
            this.constantOrUnknown = constant;
        } else {
            throw new RuntimeException("Cannot create pair");
        }
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

    @Override
    public boolean isConstantOrUnknown() {
        return false;
    }

    @Override
    public boolean isPair() {
        return true;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    public void setConstant(ConstantOrUnknown constantOrUnknown) {
        this.constantOrUnknown = constantOrUnknown;
    }

    public ConstantOrUnknown getConstantOrUnknown() {
        return constantOrUnknown;
    }

    public void setConstantOrUnknown(ConstantOrUnknown constantOrUnknown) {
        this.constantOrUnknown = constantOrUnknown;
    }

    public String toString() {
        return "(" + this.getParameter() + "," + this.getConstant() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(parameter, pair.parameter) && Objects.equals(constantOrUnknown, pair.constantOrUnknown);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, constantOrUnknown);
    }

    @Override
    public Pair copy() {
        return new Pair(this.getParameter(), this.getConstant());
    }

    public int compareTo(Object o) {
        return this.getParameter().compareTo((((Pair) o).getParameter()));
    }

    @Override
    public boolean isInstanceOf(LocationValue otherValue) {

        if (!(otherValue instanceof Pair)) {
            return false;
        }

        Pair otherValueAsPair = (Pair) otherValue;

        if (this.equals(otherValueAsPair)) {
            return true;
        }

        if (this.getParameter().equals(otherValueAsPair.getParameter())) {

            ConstantOrUnknown thisBinding = this.getConstantOrUnknown();
            ConstantOrUnknown otherBinding = otherValueAsPair.getConstantOrUnknown();

            return (thisBinding.equals(otherBinding) || otherBinding.isUnknown());
        }

        return false;
    }
}
