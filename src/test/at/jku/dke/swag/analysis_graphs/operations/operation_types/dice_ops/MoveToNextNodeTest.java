package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;


import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

public class MoveToNextNodeTest {

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
    @DisplayName("When actual dice node is constant and not last")
    class ActualNodeIsConstantAndNotLast {

        @Test
        @DisplayName("When actual dice node is constant and not last")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveToNextNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM));
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
    @DisplayName("When actual dice node is constant and not last but not a member of dice level")
    class ActualNodeIsConstantAndNotLastButNoMember {

        @Test
        @DisplayName("When actual dice node is constant and not last but not a member of dice level")
        void added1() {
            Assertions.assertThrows(Exception.class, () -> {
                source = createSource();
                target = createSource();
                ops = initOperations();
                opTarget = Utils.evaluateAndFire(source, ops);
                opTarget.AssertValidSituation();
            });
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveToNextNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.AUSTRIA);
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is constant and last")
    class ActualNodeIsConstantAndLast {

        @Test
        @DisplayName("When actual dice node is constant and  last")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveToNextNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, AppConstants.UK);
            return as;
        }
    }

    @Nested
    @DisplayName("When actual dice node is a bound value of a parameter and not last")
    class ActualNodeIsBoundAndNotLast {

        @Test
        @DisplayName("When actual dice node  is a bound value of a parameter and not last")
        void added1() {
            source = createSource();
            target = createTarget();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveToNextNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM));
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
    @DisplayName("When actual dice node is a bound value of a parameter and last")
    class ActualNodeIsBoundAndLast {

        @Test
        @DisplayName("When actual dice node is a bound value of a parameter and last")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveToNextNode.getInstance(), List.of(AppConstants.DESTINATION_DIM));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, AppConstants.UK));
            return as;
        }
    }

    /**
     *
     */

    @Nested
    @DisplayName("When actual dice node is unbound")
    class ActualNodeIsUnbound {

        @Test
        @DisplayName("When actual dice node is unbound")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveToNextNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()));
            return as;
        }
    }

    /**
     *
     */

    @Nested
    @DisplayName("When actual dice level is unbound")
    class ActualLevelIsUnbound {

        @Test
        @DisplayName("When actual dice level is unbound")
        void added1() {
            source = createSource();
            target = createSource();
            ops = initOperations();
            opTarget = Utils.evaluateAndFire(source, ops);
            Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
            Assertions.assertEquals(opTarget, target);
        }

        public Set<Operation> initOperations() {
            Operation op3 = new Operation(MoveToNextNode.getInstance(),
                    List.of(AppConstants.DESTINATION_DIM));
            return Set.of(op3);
        }

        public AnalysisSituation createSource() {
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceLevel(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GRAN_PARAM, Level.unknown()));
            as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()));
            return as;
        }
    }

}
