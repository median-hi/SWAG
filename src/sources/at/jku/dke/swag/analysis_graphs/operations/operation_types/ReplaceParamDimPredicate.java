package at.jku.dke.swag.analysis_graphs.operations.operation_types;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.md_elements.Dimension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplaceParamDimPredicate extends OperationTypes {

    private static final ReplaceParamDimPredicate instance = new ReplaceParamDimPredicate(Collections.emptyList());

    public ReplaceParamDimPredicate(List<Object> params) {
        super(params);
    }


    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {
        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Parameter paramToRemove = (Parameter) params.get(1);
        Parameter param2 = (Parameter) params.get(2);
        ConstantOrUnknown param3 = (ConstantOrUnknown) params.get(3);

        if (situation.getDimensionSelection().get(param0).paras().contains(paramToRemove)
                && !situation.getDimensionSelection().get(param0).paras().contains(param2)) {

            BindableSet newSelection = situation.getDimensionSelection()
                    .get(param0).copy();
            newSelection.setDifference(paramToRemove);
            newSelection.union(new Pair(param2, param3));

            updates.add(
                    new Update(
                            Location.selectoinOf(param0), newSelection));
        }

        return updates;
    }
}
