package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;


import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.PairOrConstant;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class RestDiceNodeToPairTest {

    MDGraph mdGraph;
    AnalysisSituation source;
    AnalysisSituation target;

    AnalysisSituation opTarget;
    Set<Operation> ops;

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        new Pair(AppConstants.GRAN_PARAM, Level.unknown()),
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown())),
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.AUSTRIA,
                        new Pair(AppConstants.GRAN_PARAM, Level.unknown()),
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()))
        );
    }

    @BeforeEach
    void initMDGraph() {
        this.mdGraph = MDGraphInit.initMDGraph();
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    @DisplayName("When actual dice node is constant and new value is unknown")
    void added1(Parameter diceNodeParam, LevelMember diceNodeValue,
                PairOrConstant diceLevelPairOrConstant,
                PairOrConstant diceNodePairOrConstant) {
        
        source = createSource(diceLevelPairOrConstant, diceNodePairOrConstant);
        target = createTarget(diceNodeParam, diceNodeValue, diceLevelPairOrConstant);
        ops = initOperations(diceNodeParam, diceNodeValue);
        opTarget = Utils.evaluateAndFire(source, ops);
        Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
        Assertions.assertEquals(opTarget, target);
    }

    public Set<Operation> initOperations(Parameter diceNodeParam, LevelMember diceNodeValue) {
        Operation op3 = new Operation(ResetDiceNodeToPair.getInstance(),
                List.of(AppConstants.DESTINATION_DIM,
                        diceNodeParam,
                        diceNodeValue));
        return Set.of(op3);
    }

    public AnalysisSituation createSource(PairOrConstant diceLevelPairOrConstant,
                                          PairOrConstant diceNodePairOrConstant) {
        AnalysisSituation as = new AnalysisSituation(mdGraph);
        as.setDiceLevel(AppConstants.DESTINATION_DIM, diceLevelPairOrConstant);
        as.setDiceNode(AppConstants.DESTINATION_DIM, diceNodePairOrConstant);
        return as;
    }

    public AnalysisSituation createTarget(Parameter diceNodeParam, LevelMember diceNodeValue,
                                          PairOrConstant diceLevelPairOrConstant) {
        AnalysisSituation as = new AnalysisSituation(mdGraph);
        as.setDiceLevel(AppConstants.DESTINATION_DIM, diceLevelPairOrConstant);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(diceNodeParam,
                diceNodeValue));
        return as;
    }
}
