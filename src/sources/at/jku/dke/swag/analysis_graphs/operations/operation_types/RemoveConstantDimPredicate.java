package at.jku.dke.swag.analysis_graphs.operations.operation_types;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.Constant;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.md_elements.Dimension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveConstantDimPredicate extends OperationTypes {

    private static final RemoveConstantDimPredicate instance = new RemoveConstantDimPredicate(Collections.emptyList());

    public RemoveConstantDimPredicate(List<Object> params) {
        super(params);
    }


    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {
        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Constant param1 = (Constant) params.get(1);

        if (situation.getDimensionSelection().get(param0).nbConsts().contains(param1)) {

            BindableSet newSelection = situation.getDimensionSelection()
                    .get(param0).copy();
            newSelection.setDifference(param1);

            updates.add(
                    new Update(
                            Location.selectoinOf(param0), newSelection));
        }

        return updates;
    }
}
