package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;

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

public class ResetDiceLevelToConstant extends OperationTypes {

    private static final ResetDiceLevelToConstant instance = new ResetDiceLevelToConstant(Collections.emptyList());

    public ResetDiceLevelToConstant(List<Object> params) {
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

        ConstantOrUnknown actualDiceLevel = Utils.actual(situation.getDiceLevels().get(param0));

        if ((actualDiceLevel.isConstant() && !actualDiceLevel.equals(param1)) || actualDiceLevel.isPair()) {
            updates.add(
                    new Update(
                            Location.diceLevelOf(param0),
                            param1)
            );
        }

        return updates;
    }
}
