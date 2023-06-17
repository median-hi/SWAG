package at.jku.dke.swag.analysis_graphs.operations.operation_types;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.ConstantOrUnknown;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class ReplacePairDimPredicateTest {

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
    class AddedPairDoesNotExistInSelections {

        @Nested
        class RemovedPairExistInSelections {

            @Test
            void added() {
                source = createSource();
                target = createTarget();
                ops = initOperations();
                opTarget = Utils.evaluateAndFire(source, ops);
                Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
                Assertions.assertEquals(opTarget, target);
            }

            public Set<Operation> initOperations() {
                Operation op3 = new Operation(ReplaceParamDimPredicate.getInstance(),
                        List.of(AppConstants.TIME_DIM,
                                AppConstants.D_PRED_1,
                                AppConstants.D_PRED_2, AppConstants.YEAR_AFTER_2015));
                return Set.of(op3);
            }

            public AnalysisSituation createSource() {
                AnalysisSituation as = new AnalysisSituation(mdGraph);

                BindableSet selections = new BindableSet();
                selections.union(AppConstants.YEAR_AFTER_2015);
                selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
                selections.union(new Pair(AppConstants.D_PRED_1, AppConstants.YEAR_AFTER_2013));
                as.setDimensionSelection(AppConstants.TIME_DIM, selections);

                return as;
            }

            public AnalysisSituation createTarget() {
                AnalysisSituation as = new AnalysisSituation(mdGraph);

                BindableSet selections = new BindableSet();
                selections.union(AppConstants.YEAR_AFTER_2015);
                selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
                selections.union(new Pair(AppConstants.D_PRED_2, AppConstants.YEAR_AFTER_2015));

                as.setDimensionSelection(AppConstants.TIME_DIM, selections);

                return as;
            }
        }

        @Nested
        class RemovedPairDoesNotExistInSelections {

            @Test
            void added() {
                source = createSource();
                target = createSource();
                ops = initOperations();
                opTarget = Utils.evaluateAndFire(source, ops);
                Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
                Assertions.assertEquals(opTarget, target);
            }

            public Set<Operation> initOperations() {
                Operation op3 = new Operation(ReplaceParamDimPredicate.getInstance(),
                        List.of(AppConstants.TIME_DIM,
                                AppConstants.D_PRED_1,
                                AppConstants.D_PRED_2, AppConstants.YEAR_AFTER_2015));
                return Set.of(op3);
            }

            public AnalysisSituation createSource() {
                AnalysisSituation as = new AnalysisSituation(mdGraph);

                BindableSet selections = new BindableSet();
                selections.union(AppConstants.YEAR_AFTER_2015);
                selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
                as.setDimensionSelection(AppConstants.TIME_DIM, selections);

                return as;
            }
        }
    }

    @Nested
    class AddedPairExistInSelections {

        @Nested
        class RemovedPairExistInSelections {

            @Test
            void added() {
                source = createSource();
                target = createSource();
                ops = initOperations();
                opTarget = Utils.evaluateAndFire(source, ops);
                Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
                Assertions.assertEquals(opTarget, target);
            }

            public Set<Operation> initOperations() {
                Operation op3 = new Operation(ReplaceParamDimPredicate.getInstance(),
                        List.of(AppConstants.TIME_DIM,
                                AppConstants.D_PRED_1,
                                AppConstants.D_PRED_2, AppConstants.YEAR_AFTER_2015));
                return Set.of(op3);
            }

            public AnalysisSituation createSource() {
                AnalysisSituation as = new AnalysisSituation(mdGraph);

                BindableSet selections = new BindableSet();
                selections.union(AppConstants.YEAR_AFTER_2015);
                selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
                selections.union(new Pair(AppConstants.D_PRED_1, AppConstants.YEAR_AFTER_2013));
                selections.union(new Pair(AppConstants.D_PRED_2, AppConstants.YEAR_AFTER_2015));
                as.setDimensionSelection(AppConstants.TIME_DIM, selections);

                return as;
            }
        }

        @Nested
        class RemovedPairDoesNotExistInSelections {

            @Test
            void added() {
                source = createSource();
                target = createSource();
                ops = initOperations();
                opTarget = Utils.evaluateAndFire(source, ops);
                Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
                Assertions.assertEquals(opTarget, target);
            }

            public Set<Operation> initOperations() {
                Operation op3 = new Operation(ReplaceParamDimPredicate.getInstance(),
                        List.of(AppConstants.TIME_DIM,
                                AppConstants.D_PRED_1,
                                AppConstants.D_PRED_2, AppConstants.YEAR_AFTER_2015));
                return Set.of(op3);
            }

            public AnalysisSituation createSource() {
                AnalysisSituation as = new AnalysisSituation(mdGraph);

                BindableSet selections = new BindableSet();
                selections.union(AppConstants.YEAR_AFTER_2015);
                selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
                selections.union(new Pair(AppConstants.D_PRED_2, AppConstants.YEAR_AFTER_2015));
                as.setDimensionSelection(AppConstants.TIME_DIM, selections);

                return as;
            }
        }
    }
}
