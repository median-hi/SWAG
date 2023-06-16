package at.jku.dke.swag.analysis_graphs.operations.operation_types;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.OperationTypes;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Hierarchy;
import at.jku.dke.swag.md_elements.Level;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RollUp extends OperationTypes {

    private static final RollUp instance = new RollUp(Collections.emptyList());

    public RollUp(List<Object> params) {
        super(params);
    }

    public static OperationTypes getInstance() {
        return instance;
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {

        Set<Update> updates = new HashSet<>();

        Dimension param0 = (Dimension) params.get(0);
        Hierarchy param1 = (Hierarchy) params.get(1);

        ConstantOrUnknown actualGran = Utils.actual(situation.getGranularities().get(param0));

        //System.out.println("BEFORE");

        if (!actualGran.isStrictlyUnknown()
                && mdGraph.isLevelInHierarchy(param1, (Level) actualGran)
                && !actualGran.equals(situation.getMdGraph().top(param0))
                && actualGran.isPair()) {

            Pair newGranPair = ((Pair) situation.getGranularities()
                    .get(param0)).copy();
            newGranPair.setConstant(mdGraph.nextLevel(param1, (Level) actualGran).get());
            updates.add(
                    new Update(
                            Location.granularityOf(param0), newGranPair));
            //System.out.println("producing update set");
        } else {
            if (!actualGran.isStrictlyUnknown()
                    && mdGraph.isLevelInHierarchy(param1, (Level) actualGran)
                    && !actualGran.equals(situation.getMdGraph().top(param0))
                    && !actualGran.equals(mdGraph.nextLevel(param1, (Level) actualGran).get())
                    && actualGran.isConstantOrUnknown()) {

                Level newGranPair = mdGraph.nextLevel(param1, (Level) actualGran).get();
                updates.add(
                        new Update(
                                Location.granularityOf(param0),
                                newGranPair)
                );
                //System.out.println("producing empty set");
            } else {
                if (actualGran.isStrictlyUnknown()) {
                    updates.add(
                            new Update(
                                    Location.granularityOf(param0),
                                    Level.unknown())
                    );
                }

            }
        }

        return updates;
    }
}
