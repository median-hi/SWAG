package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.Copiable;
import at.jku.dke.swag.analysis_graphs.asm_elements.LocationValue;

public interface PairOrConstant extends LocationValue, Copiable {

    public boolean isUnknown();

    public boolean isConstant();

    public boolean isConstantOrUnknown();

    public boolean isPair();

    public default boolean isPairOrConstant() {
        return true;
    }

    public default boolean isBindableSet() {
        return false;
    }
}
