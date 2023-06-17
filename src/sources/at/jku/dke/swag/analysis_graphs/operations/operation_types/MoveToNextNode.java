package at.jku.dke.swag.analysis_graphs.operations.operation_types;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveToNextNode extends OperationTypes {

    private static final MoveToNextNode instance = new MoveToNextNode(Collections.emptyList());

    public MoveToNextNode(List<Object> params) {
        super(params);
    }

    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {

        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        ConstantOrUnknown actualDiceNode = Utils.actual(situation.getDiceLevels().get(param0));
        ConstantOrUnknown actualDiceLevel = Utils.actual(situation.getDiceNodes().get(param0));

        if (!actualDiceLevel.isUnknown()
                && !actualDiceNode.isUnknown()
                && !actualDiceNode.equals(situation.getMdGraph().lastMember((Level) actualDiceLevel))
                && actualDiceNode.isPair()
                && DiceUtils.isLegalDiceNodePair(situation,
                param0,
                mdGraph.nextMember((Level) actualDiceNode,
                        (LevelMember) actualDiceLevel))) {

            Pair newPair = (Pair) situation.getDiceNodes().get(param0).copy();
            newPair.setConstant(mdGraph.nextMember((Level) actualDiceLevel,
                    (LevelMember) actualDiceNode));
            updates.add(
                    new Update(
                            Location.diceNodeOf(param0), newPair));
        } else {
            if (!actualDiceLevel.isUnknown()
                    && !actualDiceNode.isUnknown()
                    && !actualDiceNode.equals(situation.getMdGraph().lastMember((Level) actualDiceLevel))
                    && actualDiceNode.isConstant()
                    && !actualDiceNode.equals(mdGraph.nextMember((Level) actualDiceLevel,
                    (LevelMember) actualDiceNode))
                    && DiceUtils.isLegalDiceNode(situation,
                    param0,
                    mdGraph.nextMember((Level) actualDiceNode,
                            (LevelMember) actualDiceLevel))) {

                Pair newPair = (Pair) situation.getDiceNodes().get(param0).copy();
                newPair.setConstant(mdGraph.nextMember((Level) actualDiceLevel,
                        (LevelMember) actualDiceNode));
                updates.add(
                        new Update(
                                Location.diceNodeOf(param0), newPair));
            }
        }

        return updates;
    }
}
