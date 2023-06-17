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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RollUpTo extends OperationTypes {

    private static final RollUpTo instance = new RollUpTo(Collections.emptyList());

    public RollUpTo(List<Object> params) {
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

        if (!actualGran.isUnknown()
                && mdGraph.drillsDownToInDimension(param0, param1, (Level) actualGran)
                && actualGran.isPair()) {

            Pair newGranPair = ((Pair) situation.getGranularities()
                    .get(param0)).copy();
            newGranPair.setConstant(param1);
            updates.add(
                    new Update(
                            Location.granularityOf(param0), newGranPair));
        } else {
            if (!actualGran.isUnknown()
                    && mdGraph.drillsDownToInDimension(param0, param1, (Level) actualGran)
                    && actualGran.equals(param1)
                    && actualGran.isConstantOrUnknown()) {

                ConstantOrUnknown newGranPair = param1;
                updates.add(
                        new Update(
                                Location.granularityOf(param0),
                                param1)
                );
            }
        }

        return updates;
    }
}
