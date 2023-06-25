package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.OperationBinding;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.SituationBinding;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops.MoveToLevelAndNode_1;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops.DrillDownTo;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamDimPredicate;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamResultPredicate;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.MDGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TraceTest {

    SWAG swag = SwagInit.initSwag();
    MDGraph mdGraph = swag.getMdGraph();
    List<AnalysisSituation> situations = swag.getSituations();
    List<Step> steps = swag.getSteps();

    @Test
    void traceTest() {

        AnalysisSituation initialSituation = situations.get(1);
        SituationBinding initialAsBindings = SituationBinding
                .create(Location.diceNodeOf(AppConstants.DESTINATION_DIM))
                .setBindings(Map.of(AppConstants.CONTINENT_NODE, AppConstants.EU));

        OperationBinding bindings2_3a = OperationBinding.create().setBindings(Map.of(4, AppConstants.GERMANY));
        OperationBinding bindings2_3b = OperationBinding.create().setBindings(Map.of());
        OperationBinding bindings2_3c = OperationBinding.create().setBindings(Map.of(3, AppConstants.YEAR_AFTER_2010));

        OperationBinding bindings3_4a = OperationBinding.create().setBindings(Map.of());
        OperationBinding bindings3_4b = OperationBinding.create().setBindings(Map.of(2, AppConstants.INTENSITY_GT_30K));

        Trace trace = new Trace(initialSituation,
                initialAsBindings,
                List.of(steps.get(1), steps.get(2)),
                List.of(
                        Map.of(steps.get(1).getOperationOfType(MoveToLevelAndNode_1.class), bindings2_3a,
                                steps.get(1).getOperationOfType(DrillDownTo.class), bindings2_3b,
                                steps.get(1).getOperationOfType(AddParamDimPredicate.class), bindings2_3c),
                        Map.of(steps.get(2).getOperationOfType(DrillDownTo.class), bindings3_4a,
                                steps.get(2).getOperationOfType(AddParamResultPredicate.class), bindings3_4b)
                )
        );

        List<AnalysisSituation> res = Utils.executeTrace(trace);
        Assertions.assertEquals(createAs3Prime(), res.get(1));
        Assertions.assertEquals(createAs4Prime(), res.get(2));
    }

    public AnalysisSituation createAs3Prime() {

        AnalysisSituation as = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);

        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setGran(AppConstants.TIME_DIM, AppConstants.YEAR);

        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));

        BindableSet selections = new BindableSet();
        selections.union(new Pair(AppConstants.D_PRED, AppConstants.YEAR_AFTER_2010));
        as.setDimensionSelection(AppConstants.TIME_DIM, selections);

        return as;
    }

    public AnalysisSituation createAs4Prime() {
        AnalysisSituation as = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);

        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setGran(AppConstants.TIME_DIM, AppConstants.REF_PERIOD);

        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));

        BindableSet selections = new BindableSet();
        selections.union(new Pair(AppConstants.D_PRED, AppConstants.YEAR_AFTER_2010));
        as.setDimensionSelection(AppConstants.TIME_DIM, selections);

        BindableSet filters = new BindableSet();
        filters.union(new Pair(AppConstants.M_PRED, AppConstants.INTENSITY_GT_30K));
        as.setResultFilter(filters);

        return as;
    }

}
