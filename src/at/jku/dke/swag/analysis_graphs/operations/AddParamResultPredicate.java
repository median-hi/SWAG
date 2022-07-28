package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.analysis_graphs.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddParamResultPredicate extends OperationTypes{

    private static final AddParamResultPredicate instance = new AddParamResultPredicate(Collections.emptyList());

    public AddParamResultPredicate(List<Object> params) {
        super(params);
    }


    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {
        Set<Update> updates = new HashSet<>();

        Parameter param1 = (Parameter) params.get(0);
        Constant param2 = (Constant) params.get(1);

        if(!situation.getResultFilters().paras().contains(param1)
                && !situation.getResultFilters().consts().contains(param2)){

            BindableSet newSelection = situation.getResultFilters()
                    .copy();
            newSelection.union(new Pair(param1, param2));

            updates.add(
                    new Update(
                            Location.resultMeasure(), newSelection));
        }

        return updates;
    }
}
