package at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResetGranularityToPair extends OperationTypes {

    private static final ResetGranularityToPair instance = new ResetGranularityToPair(Collections.emptyList());

    public ResetGranularityToPair(List<Object> params) {
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
        Level param2 = (Level) params.get(2);
        updates.add(
                new Update(
                        Location.granularityOf(param0),
                        new Pair(param1, param2))
        );
        return updates;
    }
}
