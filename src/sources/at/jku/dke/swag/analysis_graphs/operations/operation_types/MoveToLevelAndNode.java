package at.jku.dke.swag.analysis_graphs.operations.operation_types;

import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;
import at.jku.dke.swag.analysis_graphs.*;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.PairOrConstant;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.utils.Utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveToLevelAndNode extends OperationTypes {

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

        //System.out.println("before");

        if((ConstantOrUnknown.isUnknown(param3)||mdGraph.isMemberOf(param3, param1))
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
            if((ConstantOrUnknown.isUnknown(param3)||mdGraph.isMemberOf(param3, param1))
                    && (!actualDiceLevel.equals(param1) || !actualDiceNode.equals(new Pair(param2, param3)))
                    && actualDiceLevel.isConstantOrUnknown()){

                Pair newPair = new Pair(param2, param3);
                updates.add(
                        new Update(
                                Location.diceNodeOf(param0), newPair));
                updates.add(
                        new Update(
                                Location.diceLevelOf(param0), param1));
            }
            //System.out.println("producing empty");
        }

        return updates;
    }
}
