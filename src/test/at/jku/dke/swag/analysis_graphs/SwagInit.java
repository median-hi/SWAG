package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.basic_elements.*;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops.MoveToLevelAndNode;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops.MoveToLevelAndNode_1;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.gran_ops.DrillDownTo;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamDimPredicate;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.selection_ops.AddParamResultPredicate;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;

import java.util.*;

public class SwagInit {

    static MDGraph mdGraph = MDGraphInit.initMDGraph();

    public static SWAG init() {

        List<Parameter> params = new ArrayList<>();
        MDGraph mdGraph = MDGraphInit.initMDGraph();

        AnalysisSituation situation1 = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        situation1.setMeasures(measures);

        situation1.setGran(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
        situation1.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
        situation1.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.CONTINENT_NODE, LevelMember.unknown()));

        Set<Operation> operations1_2 = initOperations1_2();
        AnalysisSituation situation2 = Utils.fire(situation1, Utils.evaluate(situation1, operations1_2));
        Step step1_2 = new Step(situation1, situation2, operations1_2);

        Set<Operation> operations2_3 = initOperations2_3();
        AnalysisSituation situation3 = Utils.fire(situation2, Utils.evaluate(situation1, operations2_3));

        Set<Operation> operations3_4 = initOperations3_4();
        AnalysisSituation situation4 = Utils.fire(situation3, Utils.evaluate(situation1, operations3_4));

        Set<Operation> operations2_5 = initOperations2_5();
        AnalysisSituation situation5 = Utils.fire(situation2, Utils.evaluate(situation1, operations2_5));
        Step step2_5 = new Step(situation2, situation5, operations2_5);

        Set<SituationBinding> bindings2 = Set.of(SituationBinding
                .create(Location.diceNodeOf(new Dimension("destinationDim")))
                .setBindings(Map.of(new Parameter("continentNode"), new LevelMember("EU"))));

        AnalysisSituation situation2_prime = Utils.bind(situation2, bindings2);

        Step step2_3 = new Step(situation2, situation5, operations2_3);

        Map<Operation, OperationBinding> stepBindings2_3 = new HashMap<>();

        stepBindings2_3.put(new Operation(AddParamDimPredicate.getInstance(),
                        List.of(new Dimension("timeDim"),
                                new Parameter("dPred"),
                                ConstantOrUnknown.unknown)),
                OperationBinding.create().setBindings(
                        Map.of(3, new Constant("after2010")))
        );

        stepBindings2_3.put(new Operation(MoveToLevelAndNode.getInstance(),
                        List.of(new Dimension("destinationDim"),
                                new Level("geo"),
                                new Parameter("geoNode"),
                                LevelMember.unknown())),
                OperationBinding.create().setBindings(
                        Map.of(4, new LevelMember("DE")))
        );


        stepBindings2_3.put(new Operation(DrillDownTo.getInstance(),
                        List.of(new Dimension("timeDim"),
                                new Level("year"))),
                OperationBinding.create().setBindings(
                        Map.of())
        );

        Step step2_3_prime = Utils.bind(step2_3, stepBindings2_3);
        AnalysisSituation situation3_prime = Utils.fire(situation2_prime, Utils.evaluate(situation2_prime, step2_3_prime.getOperations()));


        Step step3_4 = new Step(situation3, situation4, operations3_4);

        Map<Operation, OperationBinding> stepBindings3_4 = new HashMap<>();

        stepBindings3_4.put(new Operation(DrillDownTo.getInstance(),
                        List.of(new Dimension("timeDim"),
                                new Level("refPeriod"))),
                OperationBinding.create().setBindings(
                        Map.of())
        );

        stepBindings3_4.put(new Operation(AddParamResultPredicate.getInstance(),
                        List.of(new Parameter("mPred"),
                                ConstantOrUnknown.unknown)),
                OperationBinding.create().setBindings(
                        Map.of(2, new Constant(":highIntensity")))
        );

        Step step3_4_prime = Utils.bind(step3_4, stepBindings3_4);
        AnalysisSituation situation4_prime = Utils.fire(situation3_prime, Utils.evaluate(situation3_prime, step3_4_prime.getOperations()));

        List<AnalysisSituation> situations = new ArrayList<>();
        situations.add(situation1);
        situations.add(situation2);
        situations.add(situation3);
        situations.add(situation4);
        situations.add(situation5);

        List<Step> steps = new ArrayList<>();
        steps.add(step1_2);
        steps.add(step2_3);
        steps.add(step3_4);
        steps.add(step2_5);

        SWAG swag = new SWAG(mdGraph, situations, steps);

        System.out.println(Utils.actual(situation2.getGranularities().get(AppConstants.DESTINATION_DIM)));

        return swag;

    }


    public static List<AnalysisSituation> initAg() {

        List<Parameter> params = new ArrayList<>();


        AnalysisSituation situation1 = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        situation1.setMeasures(measures);

        situation1.setGran(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
        situation1.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
        situation1.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.CONTINENT_NODE, LevelMember.unknown()));

        Set<Operation> operations1_2 = initOperations1_2();
        AnalysisSituation situation2 = Utils.evaluateAndFire(situation1, operations1_2);

        Set<Operation> operations2_3 = initOperations2_3();
        AnalysisSituation situation3 = Utils.evaluateAndFire(situation2, operations2_3);

        Set<Operation> operations3_4 = initOperations3_4();
        AnalysisSituation situation4 = Utils.evaluateAndFire(situation3, operations3_4);

        Set<Operation> operations2_5 = initOperations2_5();
        AnalysisSituation situation5 = Utils.evaluateAndFire(situation2, operations2_5);

        List<AnalysisSituation> situations = new ArrayList<>();
        situations.add(situation1);
        situations.add(situation2);
        situations.add(situation3);
        situations.add(situation4);
        situations.add(situation5);

        return situations;

    }

    public static AnalysisSituation createAs1() {
        AnalysisSituation as = new AnalysisSituation(mdGraph);
        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);
        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.CONTINENT_NODE, LevelMember.unknown()));
        return as;
    }

    public static AnalysisSituation createAs2() {
        AnalysisSituation as = new AnalysisSituation(mdGraph);
        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);
        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.CONTINENT);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.CONTINENT_NODE, LevelMember.unknown()));
        return as;
    }

    public static AnalysisSituation createAs3() {
        AnalysisSituation as = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);

        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setGran(AppConstants.TIME_DIM, AppConstants.YEAR);

        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, LevelMember.unknown()));

        BindableSet selections = new BindableSet();
        selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
        as.setDimensionSelection(AppConstants.TIME_DIM, selections);

        return as;
    }

    public static AnalysisSituation createAs4() {
        AnalysisSituation as = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);

        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setGran(AppConstants.TIME_DIM, AppConstants.REF_PERIOD);

        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, LevelMember.unknown()));

        BindableSet selections = new BindableSet();
        selections.union(new Pair(AppConstants.D_PRED, ConstantOrUnknown.unknown));
        as.setDimensionSelection(AppConstants.TIME_DIM, selections);

        BindableSet filters = new BindableSet();
        filters.union(new Pair(AppConstants.M_PRED, ConstantOrUnknown.unknown));
        as.setResultFilter(filters);

        return as;
    }

    public static AnalysisSituation createAs5() {
        AnalysisSituation as = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);

        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setGran(AppConstants.CITIZENSHIP_DIM, AppConstants.CITIZEN);

        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE_1, LevelMember.unknown()));

        return as;
    }

    public static Set<Operation> initOperations1_2() {
        Operation op = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo")));

        return Set.of(op);
    }

    public static Set<Operation> initOperations2_3() {

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Level("year")));

        Operation op2 = new Operation(MoveToLevelAndNode_1.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo"),
                        new Parameter("geoNode"),
                        LevelMember.unknown()));

        Operation op3 = new Operation(AddParamDimPredicate.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Parameter("dPred"),
                        ConstantOrUnknown.unknown));

        return Set.of(op1, op2, op3);
    }

    public static Set<Operation> initOperations2_5() {

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("citizenshipDim"),
                        new Level("citizen")));

        Operation op2 = new Operation(MoveToLevelAndNode.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo"),
                        new Parameter("geoNode1"),
                        LevelMember.unknown()));

        return Set.of(op1, op2);
    }


    public static Set<Operation> initOperations3_4() {

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Level("refPeriod")));

        Operation op2 = new Operation(AddParamResultPredicate.getInstance(),
                List.of(new Parameter("mPred"),
                        ConstantOrUnknown.unknown));

        return Set.of(op1, op2);
    }
}
