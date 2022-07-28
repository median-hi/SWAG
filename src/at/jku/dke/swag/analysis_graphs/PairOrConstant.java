package at.jku.dke.swag.analysis_graphs;

public interface PairOrConstant extends LocationValue, Copiable {

    public boolean isConstant();
    public boolean isPair();

    public default boolean isPairOrConstant(){
        return true;
    }

    public default boolean isBindableSet(){
        return false;
    }
}
