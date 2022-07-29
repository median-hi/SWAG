package at.jku.dke.swag.analysis_graphs.init;

import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.basic_elements.*;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import at.jku.dke.swag.analysis_graphs.*;
import at.jku.dke.swag.analysis_graphs.operations.*;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.AddParamDimPredicate;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.AddParamResultPredicate;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.DrillDownTo;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.MoveToLevelAndNode;
import at.jku.dke.swag.analysis_graphs.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SwagInit {

    public static void main (String [] args) {
        MDGraph mdGraph = MDGraphInit.initMDGraph();

        AnalysisSituation situation1 = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        situation1.setMeasures(measures);

        Dimension destinationDim = new Dimension("destinationDim");
        Level continent = new Level("continent");
        Parameter continentNode = new Parameter("continentNode");

        situation1.setGran(destinationDim, continent);
        situation1.setDiceLevel(destinationDim, continent);
        situation1.setDiceNode(destinationDim, new Pair(continentNode, ConstantOrUnknown.unknown));

        Set<Operation> operations1_2 = initOperations1_2();
        AnalysisSituation situation2 = Utils.fire(situation1, Utils.evaluate(situation1, operations1_2));

        Set<Operation> operations2_3 = initOperations2_3();
        AnalysisSituation situation3 = Utils.fire(situation2, Utils.evaluate(situation1, operations2_3));

        Set<Operation> operations3_4 = initOperations3_4();
        AnalysisSituation situation4 = Utils.fire(situation3, Utils.evaluate(situation1, operations3_4));

        Set<Operation> operations2_5 = initOperations2_5();
        AnalysisSituation situation5 = Utils.fire(situation2, Utils.evaluate(situation1, operations2_5));

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
                        new LevelMember(ConstantOrUnknown.unknown.getUri()))),
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

        System.out.println(Utils.actual(situation2.getGranularities().get(destinationDim)));

    }

    public static Set<Operation> initOperations1_2(){
        Operation op = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo")));

        return Set.of(op);
    }

    public static Set<Operation> initOperations2_3(){

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Level("year")));

        Operation op2 = new Operation(MoveToLevelAndNode.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo"),
                        new Parameter("geoNode"),
                        new LevelMember(ConstantOrUnknown.unknown.getUri())));

        Operation op3 = new Operation(AddParamDimPredicate.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Parameter("dPred"),
                        ConstantOrUnknown.unknown));

        return Set.of(op1, op2, op3);
    }

    public static Set<Operation> initOperations2_5(){

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("citizenshipDim"),
                        new Level("citizen")));

        Operation op2 = new Operation(MoveToLevelAndNode.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo"),
                        new Parameter("geoNode1"),
                        new LevelMember(ConstantOrUnknown.unknown.getUri())));

        return Set.of(op1, op2);
    }


    public static Set<Operation> initOperations3_4(){

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Level("refPeriod")));

        Operation op2 = new Operation(AddParamResultPredicate.getInstance(),
                List.of(new Parameter("mPred"),
                        ConstantOrUnknown.unknown));

        return Set.of(op1, op2);
    }
}
