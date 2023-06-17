package at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddParamResultPredicate extends OperationTypes {

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
        ConstantOrUnknown param2 = (ConstantOrUnknown) params.get(1);

        if (!situation.getResultFilters().paras().contains(param1)) {

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
