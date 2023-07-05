package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.basic_elements.OperationBinding;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.basic_elements.SituationBinding;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops.MoveToLevelAndNode_1;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops.DrillDownTo;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamDimPredicate;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamResultPredicate;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.MDGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StepBindingTest {

    SWAG swag = SwagInit.initSwag();
    MDGraph mdGraph = swag.getMdGraph();
    List<AnalysisSituation> situations = swag.getSituations();
    List<Step> steps = swag.getSteps();

    public static Set<Operation> initOperations2_3Prime() {

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Level("year")));

        Operation op2 = new Operation(MoveToLevelAndNode_1.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo"),
                        new Parameter("geoNode"),
                        AppConstants.GERMANY));

        Operation op3 = new Operation(AddParamDimPredicate.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Parameter("dPred"),
                        AppConstants.YEAR_AFTER_2010));

        return Set.of(op1, op2, op3);
    }

    public static Set<Operation> initOperations3_4Prime() {

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Level("refPeriod")));

        Operation op2 = new Operation(AddParamResultPredicate.getInstance(),
                List.of(new Parameter("mPred"),
                        AppConstants.INTENSITY_GT_30K));

        return Set.of(op1, op2);
    }

    @Test
    @DisplayName("When a navigation step is bound, the resulting step (or set of operations) is as expected")
    void bindingStepResultsExpectedResults() {

        AnalysisSituation initialSituation = situations.get(1);
        SituationBinding initialAsBindings = SituationBinding
                .create(Location.diceNodeOf(AppConstants.DESTINATION_DIM))
                .setBindings(Map.of(AppConstants.CONTINENT_NODE, AppConstants.EU));

        OperationBinding bindings2_3a = OperationBinding.create().setBindings(Map.of(4, AppConstants.GERMANY));
        OperationBinding bindings2_3b = OperationBinding.create().setBindings(Map.of());
        OperationBinding bindings2_3c = OperationBinding.create().setBindings(Map.of(3, AppConstants.YEAR_AFTER_2010));

        Step step2_3Prime = Utils.bind(steps.get(1), Map.of(steps.get(1).getOperationOfType(MoveToLevelAndNode_1.class), bindings2_3a,
                steps.get(1).getOperationOfType(DrillDownTo.class), bindings2_3b,
                steps.get(1).getOperationOfType(AddParamDimPredicate.class), bindings2_3c));

        OperationBinding bindings3_4a = OperationBinding.create().setBindings(Map.of());
        OperationBinding bindings3_4b = OperationBinding.create().setBindings(Map.of(2, AppConstants.INTENSITY_GT_30K));

        Step step3_4Prime = Utils.bind(steps.get(2), Map.of(steps.get(2).getOperationOfType(DrillDownTo.class), bindings3_4a,
                steps.get(2).getOperationOfType(AddParamResultPredicate.class), bindings3_4b));

        Assertions.assertEquals(initOperations2_3Prime(), step2_3Prime.getOperations());
        Assertions.assertEquals(initOperations3_4Prime(), step3_4Prime.getOperations());
    }

}
