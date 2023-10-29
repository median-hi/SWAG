package at.jku.dke.swag.analysis_graphs.asm_elements;

public interface LocationValue {
    public boolean isBindableSet();

    public boolean isPairOrConstant();

    public default boolean isInstanceOf(LocationValue otherValue) {
        return true;
    }
}
