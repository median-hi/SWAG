package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;


import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

public class MoveAsideToNodeTest {

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
    @DisplayName("When actual dice node is constant and new value is different")
    class ActualNodeIsConstant {

        @Test
        @DisplayName("When actual dice node is constant and new value is different")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GERMANY));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.AUSTRIA);
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.GERMANY);
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is constant and new value is same")
    class ActualNodeIsConstantAndNewValueIsSame {

        @Test
        @DisplayName("When actual dice node is constant and new value is same")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.AUSTRIA));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.AUSTRIA);
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is constant but new value is not member of dice level")
    class ActualNodeIsConstantAndButNewValueIsNoMember {

        @Test
        @DisplayName("When actual dice node is constant but new value is not a member of dice level")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.FAKE));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.AUSTRIA);
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.AUSTRIA);
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is constant but dice level is an unbound pair")
    class ActualNodeIsConstantButLevelUnbound {

        @Test
        @DisplayName("When actual dice node is constant but dice level is an unbound pair")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GERMANY));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, Level.unknown()));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is constant but dice level is a bound pair")
    class ActualNodeIsConstantAndLevelIsBound {

        @Test
        @DisplayName("When actual dice node is constant but dice level is a bound pair")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GERMANY));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO));
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.AUSTRIA);
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO));
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.GERMANY);
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is a bound value of a parameter")
    class ActualNodeIsBound {

        @Test
        @DisplayName("When actual dice node is a bound value of a parameter")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GERMANY));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA));
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, AppConstants.GERMANY));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is a bound value of a parameter but new value is not member of the dice level")
    class ActualNodeIsBoundButNewValueIsNoMember {

        @Test
        @DisplayName("When actual dice node is a bound value of a parameter but new value is not member of the dice level")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.FAKE));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is a bound value of a parameter but dice level is unbound")
    class ActualNodeIsBoundButDiceLevelIsUnbound1 {

        @Test
        @DisplayName("When actual dice node is a bound value of a parameter but dice level is unbound")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GERMANY));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, Level.unknown()));
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is a bound value of a parameter but dice level is bound parameter")
    class ActualNodeIsBoundButDiceLevelIsUnbound2 {

        @Test
        @DisplayName("When actual dice node is a bound value of a parameter but dice level is bound parameter")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveAsideToNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM, AppConstants.GERMANY));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO));
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA));
            return as;
        }

        public AnalysisSituation createTarget() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO));
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, AppConstants.GERMANY));
            return as;
        }
    }
}
