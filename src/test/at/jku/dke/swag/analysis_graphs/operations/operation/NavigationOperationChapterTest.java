package at.jku.dke.swag.analysis_graphs.operations.operation;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.SwagInit;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.asm_elements.Update;
import at.jku.dke.swag.analysis_graphs.asm_elements.UpdateSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.Constant;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops.DrillDown;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops.RollUp;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamDimPredicate;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DisplayName("Test Chapter 6 examples")
public class NavigationOperationChapterTest {

    @Test
    @DisplayName("chapter#6 example#2 Inconsistent update set")
    void testInconsistentUpdate() {
        Update upd1 = new Update(
                Location.diceNodeOf(AppConstants.DESTINATION_DIM), new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));
        Update upd2 = new Update(
                Location.diceNodeOf(AppConstants.DESTINATION_DIM), new Pair(AppConstants.GEO_NODE, AppConstants.AUSTRIA));
        Set<Update> updSet = new HashSet<>();
        updSet.add(upd1);
        updSet.add(upd2);
        
        Assertions.assertFalse(UpdateSet.verifyConsistent(updSet));
    }

    @Test
    @DisplayName("chapter#6 example#1 Update set application")
    void testUpdateSetApplication() {
        AnalysisSituation as3 = SwagInit.createAs3();
        Update upd1 = new Update(
                Location.diceNodeOf(AppConstants.DESTINATION_DIM), new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));
        AnalysisSituation as3_prime = Utils.fire(as3, upd1);
        AnalysisSituation as3_prime_expected = as3.copy();
        as3_prime_expected.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));

        Assertions.assertEquals(as3_prime, as3_prime_expected);
    }

    @Test
    @DisplayName("chapter#6 example#5 Inconsistent set of ops")
    void testOperationEvaluation() {
        Operation op1 = new Operation(AddParamDimPredicate.getInstance(),
                List.of(AppConstants.TIME_DIM, AppConstants.D_PRED, Constant.unknown()));
        Set<Operation> ops = Set.of(op1);
        AnalysisSituation as2 = SwagInit.createAs2();
        BindableSet set = new BindableSet();
        set.union(new Pair(AppConstants.D_PRED, Constant.unknown()));
        Update upd = new Update(
                Location.selectoinOf(AppConstants.TIME_DIM), set);

        Assertions.assertEquals(Set.of(upd), Utils.evaluate(as2, ops));
    }

    @Test
    @DisplayName("chapter#6 example#6 Inconsistent set of ops")
    void testInconsistentSetOfOps() {
        Operation op1 = new Operation(DrillDown.getInstance(),
                List.of(AppConstants.TIME_DIM, AppConstants.TIME_HIER));
        Operation op2 = new Operation(RollUp.getInstance(),
                List.of(AppConstants.TIME_DIM, AppConstants.TIME_HIER));
        Set<Operation> ops = Set.of(op1, op2);
        AnalysisSituation as3 = SwagInit.createAs3();

        Assertions.assertThrows(RuntimeException.class, () -> Utils.evaluateAndFire(as3, ops));
    }
}
