package at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResetGranularityToConstant extends OperationTypes {

    private static final ResetGranularityToConstant instance = new ResetGranularityToConstant(Collections.emptyList());

    public ResetGranularityToConstant(List<Object> params) {
        super(params);
    }

    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {
        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Level param1 = (Level) params.get(1);

        ConstantOrUnknown actualGran = Utils.actual(situation.getGranularities().get(param0));

        if ((actualGran.isConstant() && !actualGran.equals(param1)) || actualGran.isPair()) {
            updates.add(
                    new Update(
                            Location.granularityOf(param0),
                            param1)
            );
        }

        return updates;
    }
}
