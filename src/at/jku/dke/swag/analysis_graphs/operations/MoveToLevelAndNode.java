package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.MDElems.Level;
import at.jku.dke.swag.MDElems.LevelMember;
import at.jku.dke.swag.analysis_graphs.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveToLevelAndNode extends OperationTypes{

    private static final MoveToLevelAndNode instance = new MoveToLevelAndNode(Collections.emptyList());


    public static OperationTypes getInstance() {
        return instance;
    }


    public MoveToLevelAndNode(List<Object> params) {
        super(params);
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {
        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Level param1 = (Level) params.get(1);
        Parameter param2 = (Parameter) params.get(2);
        LevelMember param3 = (LevelMember) params.get(3);

        PairOrConstant actualDiceNode = Utils.actual(situation.getDiceLevels().get(param0));
        PairOrConstant actualDiceLevel = Utils.actual(situation.getDiceNodes().get(param0));

        System.out.println("before");

        if((Constant.isUnknown(param3)||mdGraph.isMemberOf(param3, param1))
                && (!actualDiceLevel.equals(param1) || !actualDiceNode.equals(new Pair(param2, param3)))
                && actualDiceLevel.isPair()
                && !actualDiceLevel.equals(param1)){

            Pair newPair = new Pair(param2, param3);

            Pair diceLevelPair = ((Pair)actualDiceLevel).copy();
            diceLevelPair.setConstant(param1);

            updates.add(
                    new Update(
                            Location.diceNodeOf(param0), newPair));
            updates.add(
                    new Update(
                            Location.diceLevelOf(param0), diceLevelPair));

        }else{
            if((Constant.isUnknown(param3)||mdGraph.isMemberOf(param3, param1))
                    && (!actualDiceLevel.equals(param1) || !actualDiceNode.equals(new Pair(param2, param3)))
                    && actualDiceLevel.isConstant()){

                Pair newPair = new Pair(param2, param3);
                updates.add(
                        new Update(
                                Location.diceNodeOf(param0), newPair));
                updates.add(
                        new Update(
                                Location.diceLevelOf(param0), param1));
            }
            System.out.println("producing empty");
        }

        return updates;
    }
}
