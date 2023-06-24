package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveToLevelAndNode_1 extends OperationTypes {

    private static final MoveToLevelAndNode_1 instance = new MoveToLevelAndNode_1(Collections.emptyList());


    public MoveToLevelAndNode_1(List<Object> params) {
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
        Parameter param2 = (Parameter) params.get(2);
        LevelMember param3 = (LevelMember) params.get(3);

        Operation op1 = new Operation(ChangeDiceLevelTo.getInstance(), List.of(param0, param1));
        Operation op2 = new Operation(ResetDiceNodeToPair.getInstance(), List.of(param0, param2, param3));

        return Utils.conditionalUnion(situation, Set.of(op1), Set.of(op2));
    }
}
