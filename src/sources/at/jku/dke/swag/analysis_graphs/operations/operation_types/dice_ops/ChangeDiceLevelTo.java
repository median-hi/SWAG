package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChangeDiceLevelTo extends OperationTypes {

    private static final ChangeDiceLevelTo instance = new ChangeDiceLevelTo(Collections.emptyList());

    public ChangeDiceLevelTo(List<Object> params) {
        super(params);
    }

    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {

        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Level newDiceLevel = (Level) params.get(1);

        ConstantOrUnknown actualDiceLevel = Utils.actual(situation.getDiceLevels().get(param0));

        if (situation.getDiceLevels().get(param0).isPair()
                && DiceUtils.isLegalDiceLevelPair(situation, param0, newDiceLevel)) {

            Pair newPair = (Pair) situation.getDiceLevels().get(param0).copy();
            newPair.setConstant(newDiceLevel);
            updates.add(new Update(Location.diceLevelOf(param0), newPair));

        }

        if (situation.getDiceLevels().get(param0).isConstant()
                && !actualDiceLevel.equals(newDiceLevel)) {

            updates.add(new Update(Location.diceLevelOf(param0), newDiceLevel));
        }

        return updates;
    }
}
