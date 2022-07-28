package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.MDElems.Level;
import at.jku.dke.swag.analysis_graphs.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class DrillDownTo extends OperationTypes{

    private static final DrillDownTo instance = new DrillDownTo(Collections.emptyList());

    public static OperationTypes getInstance() {
        return instance;
    }

    public DrillDownTo(List<Object> params) {
        super(params);
    }

    @Override
    public Set<Update> updSet(AnalysisSituation situation, List<Object> params) {

                    Set<Update> updates = new HashSet<>();

                    Dimension param0 = (Dimension) params.get(0);
                    Level param1 = (Level) params.get(1);

                    PairOrConstant actualGran = Utils.actual(situation.getGranularities().get(param0));

                    System.out.println("BEFORE");

                    if(!actualGran.equals(Constant.unknown)
                            && !actualGran.equals(situation.getMdGraph().bot(param0))
                            && !actualGran.equals(param1)
                            && actualGran.isPair()){

                        Pair newGranPair = ((Pair)situation.getGranularities()
                                .get(param0)).copy();
                        newGranPair.setConstant(param1);
                        updates.add(
                                new Update(
                                        Location.granularityOf(param0), newGranPair));
                        System.out.println("producing update set");
                    }else{
                        if(!actualGran.equals(Constant.unknown)
                                && !actualGran.equals(situation.getMdGraph().bot(param0))
                                && !actualGran.equals(param1)
                                && actualGran.isConstant()){

                            Constant newGranPair = param1;
                            updates.add(
                                    new Update(
                                            Location.granularityOf(param0),
                                            param1)
                            );
                            System.out.println("producing empty set");
                        }
                    }

                    return updates;
    }
}
