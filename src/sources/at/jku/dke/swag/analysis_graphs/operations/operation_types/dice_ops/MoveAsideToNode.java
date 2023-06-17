package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.LevelMember;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveAsideToNode extends OperationTypes {

    private static final MoveAsideToNode instance = new MoveAsideToNode(Collections.emptyList());

    public MoveAsideToNode(List<Object> params) {
        super(params);
    }

    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {

        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        LevelMember member = (LevelMember) params.get(1);

        ConstantOrUnknown actualDiceLevel = Utils.actual(situation.getDiceLevels().get(param0));
        ConstantOrUnknown actualDiceNode = Utils.actual(situation.getDiceNodes().get(param0));

        if (situation.getDiceNodes().get(param0).isPair()
                && DiceUtils.isLegalDiceNodePair(situation, param0, member)) {

            Pair newPair = (Pair) situation.getDiceNodes().get(param0).copy();
            newPair.setConstant(member);
            updates.add(new Update(Location.diceNodeOf(param0), newPair));

        }

        if (situation.getDiceNodes().get(param0).isConstant()
                && !actualDiceNode.equals(member)
                && DiceUtils.isLegalDiceNode(situation, param0, member)) {

            updates.add(new Update(Location.diceNodeOf(param0), member));
        }
        
        return updates;
    }
}
