package at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops.DiceUtils;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetGranularity extends OperationTypes {

    private static final SetGranularity instance = new SetGranularity(Collections.emptyList());

    public SetGranularity(List<Object> params) {
        super(params);
    }

    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {

        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Level newGran = (Level) params.get(1);

        ConstantOrUnknown actualGran = Utils.actual(situation.getGranularities().get(param0));

        if (situation.getGranularities().get(param0).isPair()
                && DiceUtils.isLegalDiceLevelPair(situation, param0, newGran)) {

            Pair newPair = (Pair) situation.getGranularities().get(param0).copy();
            newPair.setConstant(newGran);
            updates.add(new Update(Location.granularityOf(param0), newPair));

        }

        if (situation.getGranularities().get(param0).isConstant()
                && !actualGran.equals(newGran)) {

            updates.add(new Update(Location.granularityOf(param0), newGran));
        }

        return updates;
    }
}
