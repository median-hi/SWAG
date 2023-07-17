package at.jku.dke.swag.analysis_graphs.operations.operation_types.navigation_core;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.SwagInit;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.basic_elements.*;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops.DrillDown;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops.RollUp;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamDimPredicate;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DisplayName("Update sets")
public class UpdateSetTest {

    MDGraph mdGraph = MDGraphInit.initMDGraph();

    @Nested
    class FiringSingleUpdateTest {

        @Test
        @DisplayName("When an update is fired, the resulting situation is as expected")
        void updateFiringWorksAsExpected() {
            AnalysisSituation as3 = SwagInit.createAs3();
            Update upd = new Update(
                    Location.diceNodeOf(AppConstants.DESTINATION_DIM),
                    new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY)
            );
            AnalysisSituation as3New = Utils.fire(as3, upd);
            Assertions.assertEquals(createAs3New(), as3New);
        }

        public AnalysisSituation createAs3New() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);

            BindableSet measures = new BindableSet();
            measures.union(mdGraph.getM().stream().findAny().get());
            as.setMeasures(measures);

            as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setGran(AppConstants.TIME_DIM, AppConstants.YEAR);

            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));

            BindableSet selections = new BindableSet();
            selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
            as.setDimensionSelection(AppConstants.TIME_DIM, selections);

            return as;
        }
    }

    @Nested
    class InconsistentUpdateSetTest {

        @Test
        @DisplayName("When an inconsistent update set is fired, an exception is thrown")
        void updateFiringWorksAsExpected() {
            AnalysisSituation as3 = SwagInit.createAs3();
            Update upd1 = new Update(
                    Location.diceNodeOf(AppConstants.DESTINATION_DIM),
                    new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY)
            );
            Update upd2 = new Update(
                    Location.diceNodeOf(AppConstants.DESTINATION_DIM),
                    new Pair(AppConstants.GEO_NODE, AppConstants.AUSTRIA)
            );
            Set<Update> upds = Set.of(upd1, upd2);
            Assertions.assertThrows(RuntimeException.class, () -> {
                Utils.fireUpdates(as3, upds);
            });
        }

        @Test
        @DisplayName("When an inconsistent update set is fired, an exception is thrown")
        void updateFiringWorksAsExpected1() {
            AnalysisSituation as3 = SwagInit.createAs3();
            as3.setGran(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);

            Operation op1 = new Operation(DrillDown.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GEO_HIER));

            Operation op2 = new Operation(RollUp.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GEO_HIER));
            Set<Operation> set = Set.of(op1, op2);

            Assertions.assertThrows(RuntimeException.class, () -> {
                Utils.evaluateAndFire(as3, set);
            });
        }
    }

    @Nested
    class OperationEvaluationTest {

        @Test
        @DisplayName("When an update is fired, the resulting situation is as expected")
        void evaluationWorksAsExpected() {
            AnalysisSituation as2 = SwagInit.createAs2();
            Operation o = SwagInit.createOperation2_3_a();
            Set<Update> upds = Utils.evaluate(as2, Set.of(o));
            Set<Update> actual = Set.of(new Update(Location.selectoinOf(AppConstants.TIME_DIM),
                    new Pair(AppConstants.D_PRED, Constant.unknown())));
            Assertions.assertEquals(actual, upds);
        }
    }

    @Nested
    class OperationBindingTest {

        @DisplayName("Binding operations works as expected")
        @Test
        void bindOperationTest() {
            Operation op1 = new Operation(AddParamDimPredicate.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.D_PRED, Constant.unknown()));
            OperationBinding bindings2_3c = OperationBinding.create().setBindings(Map.of(3, AppConstants.YEAR_AFTER_2010));
            Operation op1Bound = Utils.bind(op1, bindings2_3c);

            Operation op1Expected = new Operation(AddParamDimPredicate.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.D_PRED, AppConstants.YEAR_AFTER_2010));

            Assertions.assertEquals(op1Expected, op1Bound);

        }

    }
}
