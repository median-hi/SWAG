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

    private static Stream<Arguments> provideNonEmptyUpdateSetParams() {
        return Stream.of(

                //Dice level constant
                //  Dice node constant
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        AppConstants.AUSTRIA,
                        AppConstants.GEO),
                //      new value unknown
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        AppConstants.AUSTRIA,
                        AppConstants.GEO),

                //  Dice Node bound parameter
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA),
                        AppConstants.GEO),
                //      new value unknown
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA),
                        AppConstants.GEO),

                //  Dice Node unbound parameter
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()),
                        AppConstants.GEO),
                //      new value unknown
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()),
                        AppConstants.GEO),

                //Dice level bound parameter

                //  Dice node constant
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        AppConstants.AUSTRIA,
                        new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO)),
                //      new value unknown
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        AppConstants.AUSTRIA,
                        new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO)),

                //  Dice Node bound parameter
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA),
                        new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO)),
                //      new value unknown
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA),
                        new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO)),

                //  Dice Node unbound parameter
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()),
                        new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO)),
                //      new value unknown
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()),
                        new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO)),


                //Dice level unbound parameter

                //  Dice node constant
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        AppConstants.AUSTRIA,
                        new Pair(AppConstants.GRAN_PARAM, AppConstants.GEO)),
                //      new value unknown


                //  Dice Node bound parameter
                //      new value unknown
                Arguments.of(AppConstants.DICE_PARAM, LevelMember.unknown(),
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()),
                        new Pair(AppConstants.GRAN_PARAM, Level.unknown()))
        );
    }

    private static Stream<Arguments> provideEmptyUpdateSetParams() {
        return Stream.of(
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.FAKE,
                        AppConstants.AUSTRIA,
                        AppConstants.GEO),

                //  Dice Node unbound parameter
                //      new value constant
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()),
                        new Pair(AppConstants.GRAN_PARAM, Level.unknown())),
                
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.AUSTRIA,
                        new Pair(AppConstants.DICE_PARAM, LevelMember.unknown()),
                        new Pair(AppConstants.GRAN_PARAM, Level.unknown()))
        );
    }

    private static Stream<Arguments> provideThrowsExceptionParams() {
        return Stream.of(
                Arguments.of(AppConstants.DICE_PARAM, AppConstants.GERMANY,
                        new Pair(AppConstants.DICE_PARAM, AppConstants.AUSTRIA),
                        new Pair(AppConstants.GRAN_PARAM, Level.unknown()))
        );
    }

    @BeforeEach
    void initMDGraph() {
        this.mdGraph = MDGraphInit.initMDGraph();
    }

    @ParameterizedTest
    @MethodSource("provideNonEmptyUpdateSetParams")
    @DisplayName("Values that trigger updates")
    void withUpdate(Parameter diceNodeParam, LevelMember diceNodeValue,
                    PairOrConstant diceNodePairOrConstant,
                    PairOrConstant diceLevelPairOrConstant) {

        source = createSource(diceLevelPairOrConstant, diceNodePairOrConstant);
        target = createTarget(diceNodeParam, diceNodeValue, diceLevelPairOrConstant);
        ops = initOperations(diceNodeParam, diceNodeValue);
        opTarget = Utils.evaluateAndFire(source, ops);
        Assertions.assertFalse(Utils.evaluate(source, ops).isEmpty());
        Assertions.assertEquals(opTarget, target);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyUpdateSetParams")
    @DisplayName("Values that do not trigger updates")
    void noUpdate(Parameter diceNodeParam, LevelMember diceNodeValue,
                  PairOrConstant diceNodePairOrConstant,
                  PairOrConstant diceLevelPairOrConstant) {

        source = createSource(diceLevelPairOrConstant, diceNodePairOrConstant);
        target = createSource(diceLevelPairOrConstant, diceNodePairOrConstant);
        ops = initOperations(diceNodeParam, diceNodeValue);
        opTarget = Utils.evaluateAndFire(source, ops);
        Assertions.assertTrue(Utils.evaluate(source, ops).isEmpty());
        Assertions.assertEquals(opTarget, target);
    }

    @ParameterizedTest
    @MethodSource("provideThrowsExceptionParams")
    @DisplayName("Values that cause exceptions")
    void throwsException(Parameter diceNodeParam,
                         LevelMember diceNodeValue,
                         PairOrConstant diceNodePairOrConstant,
                         PairOrConstant diceLevelPairOrConstant) {
        Assertions.assertThrows(Exception.class, () -> {
            source = createSource(diceLevelPairOrConstant, diceNodePairOrConstant);
            target = createSource(diceLevelPairOrConstant, diceNodePairOrConstant);
            ops = initOperations(diceNodeParam, diceNodeValue);
            opTarget = Utils.evaluateAndFire(source, ops);
        });
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

    public AnalysisSituation createTarget(Parameter diceNodeParam,
                                          LevelMember diceNodeValue,
                                          PairOrConstant diceLevelPairOrConstant) {
        AnalysisSituation as = new AnalysisSituation(mdGraph);
        as.setDiceLevel(AppConstants.DESTINATION_DIM, diceLevelPairOrConstant);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(diceNodeParam, diceNodeValue));
        return as;
    }
}
