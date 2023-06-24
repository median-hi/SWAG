package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

public class ResetDiceLevelToPairTest {

    MDGraph mdGraph;
    AnalysisSituation source;
    AnalysisSituation target;
    AnalysisSituation opTarget;
    Set<Operation> ops;

    @BeforeEach
    void initMDGraph() {
        this.mdGraph = MDGraphInit.initMDGraph();
    }

    @Nested
    @DisplayName("When actual granularity is constant")
    class ActualGranIsConstant {

        @Test
        @DisplayName("When actual dice level is constant")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(ResetDiceLevelToPair.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice level is constant and its value is the same as the parameter binding")
    class ActualGranIsConstantAndEqualNew {

        @Test
        @DisplayName("When actual dice level is constant and its value is the same as the parameter binding")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(ResetDiceLevelToPair.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice level is pair that its param is different to the new param and value is same in both")
    class ActualGranIsPairAndDifferentParam {

        @Test
        @DisplayName("When actual dice level is pair that its param is different to the new param and value is same in both")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(ResetDiceLevelToPair.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GRAN_PARAM_1, AppConstants.CONTINENT));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM_1, AppConstants.CONTINENT));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice level is pair that its param is the same as the new param")
    class ActualGranIsPair {

        @Test
        @DisplayName("When actual dice level is pair that its param is the same as the new param")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(ResetDiceLevelToPair.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO));
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice level is pair that is the same param and the same value as the new value")
    class ActualGranIsPairSameAsNew {

        @Test
        @DisplayName("When actual dice level is pair that is the same param and the same value as the new value")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(ResetDiceLevelToPair.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.CONTINENT));
            return as;
        }
    }

}
