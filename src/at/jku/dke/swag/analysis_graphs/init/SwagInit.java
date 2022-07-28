package at.jku.dke.swag.analysis_graphs.init;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.MDElems.Level;
import at.jku.dke.swag.MDElems.LevelMember;
import at.jku.dke.swag.MDElems.MDGraph;
import at.jku.dke.swag.MDElems.init.MDGraphInit;
import at.jku.dke.swag.analysis_graphs.*;
import at.jku.dke.swag.analysis_graphs.operations.*;

import java.util.List;
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
        situation1.setDiceNode(destinationDim, new Pair(continentNode, Constant.unknown));

        Set<Operation> operations1_2 = initOperations1_2();
        AnalysisSituation situation2 = Utils.fire(situation1, Utils.evaluate(situation1, operations1_2));

        Set<Operation> operations2_3 = initOperations2_3();
        AnalysisSituation situation3 = Utils.fire(situation2, Utils.evaluate(situation1, operations2_3));

        Set<Operation> operations3_4 = initOperations3_4();
        AnalysisSituation situation4 = Utils.fire(situation3, Utils.evaluate(situation1, operations3_4));

        Set<Operation> operations2_5 = initOperations2_5();
        AnalysisSituation situation5 = Utils.fire(situation2, Utils.evaluate(situation1, operations2_5));

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
                        new LevelMember(Constant.unknown.getUri())));

        Operation op3 = new Operation(AddParamDimPredicate.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Parameter("dPred"),
                        Constant.unknown));

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
                        new LevelMember(Constant.unknown.getUri())));

        return Set.of(op1, op2);
    }


    public static Set<Operation> initOperations3_4(){

        Operation op1 = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("timeDim"),
                        new Level("refPeriod")));

        Operation op2 = new Operation(AddParamResultPredicate.getInstance(),
                List.of(new Parameter("mPred"),
                        Constant.unknown));

        return Set.of(op1, op2);
    }
}
