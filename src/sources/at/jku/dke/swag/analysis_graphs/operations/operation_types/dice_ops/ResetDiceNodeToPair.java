package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.LevelMember;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResetDiceNodeToPair extends OperationTypes {

    private static final ResetDiceNodeToPair instance = new ResetDiceNodeToPair(Collections.emptyList());

    public ResetDiceNodeToPair(List<Object> params) {
        super(params);
    }

    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {
        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Parameter param1 = (Parameter) params.get(1);
        LevelMember member = (LevelMember) params.get(2);

        if (DiceUtils.isLegalDiceNodePair(situation, param0, param1, member)) {
            updates.add(new Update(
                    Location.granularityOf(param0),
                    new Pair(param1, member))
            );
        }

        return updates;
    }
}
