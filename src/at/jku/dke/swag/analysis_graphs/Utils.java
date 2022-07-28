package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.analysis_graphs.operations.Operation;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static Set <Update> evaluate(AnalysisSituation source, Set<Operation> operations){
        Set <Update> updateSet = new HashSet<>();

        for (Operation op : operations){
            updateSet.addAll(op.evaluate(source));
        }

        return updateSet;
    }

    public static  AnalysisSituation fire(AnalysisSituation source, Set<Update> updateSet){

        AnalysisSituation resultSituation = source.copy();

        for (Update update : updateSet){
            resultSituation = fire(resultSituation, update);
        }

        return resultSituation;
    }

    public static  AnalysisSituation fire(AnalysisSituation source, Update update){

        AnalysisSituation resultSituation = source.copy();

        if(update.getLocation().isMeasureLocation()){
            resultSituation.setMeasures((BindableSet) update.getValue());
        }

        if(update.getLocation().isResultFilterLocation()){
            resultSituation.setResultFilter((BindableSet) update.getValue());
        }

        if(update.getLocation().isDimensionUpdate()){
            String dimension = update.getLocation().getDimensionOfUpdate();

            if(update.getLocation().isGranLocation()){
                resultSituation.setGran(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if(update.getLocation().isDiceLevelLocation()){
                resultSituation.setDiceLevel(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if(update.getLocation().isDiceNodeLocation()){
                resultSituation.setDiceNode(new Dimension(dimension), (PairOrConstant) update.getValue());
            }

            if(update.getLocation().isDimensionSelectionLocation()){
                resultSituation.setDimensionSelection(new Dimension(dimension), (BindableSet) update.getValue());
            }
        }

        return resultSituation;
    }

    public static Constant actual(PairOrConstant elem){
        if(elem.isConstant()){
            return ((Constant) elem);
        }
        return ((Pair) elem).getConstant();
    }
}
