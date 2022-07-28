package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.analysis_graphs.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class AddParamDimPredicate extends OperationTypes{

    private static final AddParamDimPredicate instance = new AddParamDimPredicate(Collections.emptyList());

    public AddParamDimPredicate(List<Object> params) {
        super(params);
    }


    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {
        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Parameter param1 = (Parameter) params.get(1);
        Constant param2 = (Constant) params.get(2);

        if(!situation.getDimensionSelection().get(param0).paras().contains(param1)
                && !situation.getDimensionSelection().get(param0).consts().contains(param2)){

            BindableSet newSelection = situation.getDimensionSelection()
                    .get(param0).copy();
            newSelection.union(new Pair(param1, param2));

            updates.add(
                    new Update(
                            Location.selectoinOf(param0), newSelection));
        }

        return updates;
    }
}
