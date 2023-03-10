package at.jku.dke.swag.analysis_graphs.init;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.Step;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.basic_elements.*;
import at.jku.dke.swag.analysis_graphs.operations.Operation;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.AddParamDimPredicate;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.AddParamResultPredicate;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.DrillDownTo;
import at.jku.dke.swag.analysis_graphs.operations.operation_types.MoveToLevelAndNode;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;

import java.lang.reflect.Member;
import java.util.*;
import java.util.stream.Collectors;

public class TestDice {

    static MDGraph mdGraph = MDGraphInit.initMDGraph();

    static Set<Parameter> parametersOfLevelsDestinationDim = Set.of(new Parameter("diceLevel1"), new Parameter("diceLevel2"));

    static Set<Level> levelsOfDestinationDim = Set.of(new Level("geo"), new Level("continent"));
    static Set<Parameter> parametersOfGeo = Set.of(new Parameter("geoNode1"), new Parameter("geoNode2"));
    static Set<Parameter> parametersOfContinent = Set.of(new Parameter("continentNode1"), new Parameter("continentNode2"));
    static Set<Parameter> levelParametersOfDestinationDim = mergeSets(parametersOfGeo, parametersOfContinent);
    static Map<Level, Set<Parameter>> parametersOfLevel = Map.of(new Level("geo"), parametersOfGeo, new Level("continent"), parametersOfContinent);

    static Set<LevelMember> membersOfGeo = mdGraph.getMembersOf(new Level("geo"));
    static Set<LevelMember> membersOfContinent = mdGraph.getMembersOf(new Level("continent"));
    static Set<LevelMember> membersOfDestinationDim = mdGraph.getMembersOf(new Dimension("destinationDim"));


    public static void main (String [] args) {
        MDGraph mdGraph = MDGraphInit.initMDGraph();

        for(AnalysisSituation situation : TestDice.getSituations()){
            for (Set<SituationBinding> bindingsOuter : bindDiceNode(situation)){
                for (Set<SituationBinding> bindingsInner : bindDiceLevel(situation)){

                    AnalysisSituation boundSItuation = situation.copy();
                    boundSItuation = Utils.bind(boundSItuation, bindingsOuter);
                    boundSItuation = Utils.bind(boundSItuation, bindingsInner);

                    boundSItuation = Utils.fire(boundSItuation, Utils.evaluate(boundSItuation, initOperations2_3()));

                    AnalysisSituation targetSituation = situation.copy();
                    targetSituation = Utils.fire(targetSituation, Utils.evaluate(targetSituation, initOperations2_3()));

                    System.out.println("getDiceLevelOnDimInSituation:" + getDiceLevelOnDimInSituation(boundSItuation, new Dimension("destinationDim")));
                    System.out.println("getDiceLevelOnDimInSituation:" + getDiceNodeOnDimInSituation(boundSItuation, new Dimension("destinationDim")));
                    System.out.println("=====");
                    System.out.println("getDiceLevelOnDimInSituation:" + getDiceLevelOnDimInSituation(targetSituation, new Dimension("destinationDim")));
                    System.out.println("getDiceLevelOnDimInSituation:" + getDiceNodeOnDimInSituation(targetSituation, new Dimension("destinationDim")));
                    System.out.println("");
                    System.out.println("=============================================");
                    System.out.println("=============================================");
                }
            }
        }

    }

    public static Set<Operation> initOperations2_3(){

        Operation op2 = new Operation(MoveToLevelAndNode.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo"),
                        new Parameter("geoNode"),
                        new LevelMember("AT")));

        return Set.of(op2);
    }

    // Method to merge two sets in Java
    public static <T> Set<T> mergeSets(Set<T> a, Set<T> b)
    {
        Set<T> set = new HashSet<>();

        set.addAll(a);
        set.addAll(b);

        return set;
    }

    static Set<AnalysisSituation> getSituations(){

        Set<AnalysisSituation> situations = new HashSet<>();

        for (Map.Entry<PairOrConstant, PairOrConstant> vals : allDiceVals()){
            AnalysisSituation as = new AnalysisSituation(mdGraph);
            as.setDiceNode(new Dimension("destinationDim"), vals.getValue());
            as.setDiceLevel(new Dimension("destinationDim"), vals.getKey());
            situations.add(as);
        }
        return situations;
    }

    static Set<Map.Entry<PairOrConstant, PairOrConstant>> allDiceVals(){

        Set<Map.Entry<PairOrConstant, PairOrConstant>> set = new HashSet<>();

        // Add constants
        for (Level l : levelsOfDestinationDim){

            // Level l
            for (LevelMember m : mdGraph.getMembersOf(l)){
                set.add(createSimpleEntry(l, m));
            }
            for (Parameter p : parametersOfLevel.get(l)){
                for(LevelMember m : mdGraph.getMembersOf(l)) {
                    set.add(createSimpleEntry(l, new Pair(p,m)));
                }
                set.add(createSimpleEntry(l, createUnknownPair(p)));
            }
        }

        // level (p,)
        for (Parameter p : parametersOfLevelsDestinationDim){

            // level (p, ?)
            for (LevelMember m : membersOfDestinationDim){
                set.add(createSimpleEntry(new Pair(p, ConstantOrUnknown.unknown), m));
            }
            for (Parameter p_m : parametersOfLevel.values().stream().flatMap(Set::stream).collect(Collectors.toSet())){
                for(LevelMember m : membersOfDestinationDim) {
                    set.add(createSimpleEntry(createUnknownPair(p), new Pair(p_m,m)));
                }
                set.add(createSimpleEntry(createUnknownPair(p), createUnknownPair(p_m)));
            }

            // level (p, l)
            for (Level l : levelsOfDestinationDim){
                for (LevelMember m : membersOfDestinationDim){
                    set.add(createSimpleEntry(new Pair(p, l), m));
                }
                for (Parameter p_m : parametersOfLevel.values().stream().flatMap(Set::stream).collect(Collectors.toSet())){
                    for(LevelMember m : membersOfDestinationDim) {
                        set.add(createSimpleEntry(new Pair(p, l), new Pair(p_m,m)));
                    }
                    set.add(createSimpleEntry(new Pair(p, l), createUnknownPair(p_m)));
                }
            }
        }

        return set;
    }

    static public AbstractMap.SimpleEntry<PairOrConstant, PairOrConstant> createSimpleEntry(PairOrConstant a1, PairOrConstant a2){
        return new AbstractMap.SimpleEntry<PairOrConstant, PairOrConstant>(a1, a2);
    }

    static public Pair createUnknownPair(Parameter p){
        return new Pair(p, ConstantOrUnknown.unknown);
    }

    static public Set<OperationBinding> getAllOperationBindgins(){

        Set<OperationBinding> bindings = new HashSet<>();

        OperationBinding.create().setBindings(
                Map.of());

        return bindings;
    }

    static public Set<Set<SituationBinding>> bindDiceNode (AnalysisSituation as){

        Set<Set<SituationBinding>> bindings = new HashSet<>();

        PairOrConstant diceNode = getDiceNodeOnDimInSituation(as, new Dimension("destinationDim"));
        if(diceNode instanceof Pair){
            for (LevelMember m : membersOfDestinationDim){
                bindings.add(Set.of(SituationBinding
                        .create(Location.diceNodeOf(new Dimension("destinationDim")))
                        .setBindings(Map.of(((Pair)diceNode).getParameter(), m))));
            }
        }

        return bindings;
    }

    static public Set<Set<SituationBinding>> bindDiceLevel (AnalysisSituation as){

        Set<Set<SituationBinding>> bindings = new HashSet<>();

        PairOrConstant diceLevel = getDiceLevelOnDimInSituation(as, new Dimension("destinationDim"));
        if(diceLevel instanceof Pair){
            for (Level l : levelsOfDestinationDim){
                bindings.add(Set.of(SituationBinding
                        .create(Location.diceLevelOf(new Dimension("destinationDim")))
                        .setBindings(Map.of(((Pair)diceLevel).getParameter(), l))));
            }
        }

        return bindings;
    }


    static public PairOrConstant getDiceLevelOnDimInSituation(AnalysisSituation as, Dimension d){
        return as.getDiceLevels().get(d);
    }

    static public PairOrConstant getDiceNodeOnDimInSituation(AnalysisSituation as, Dimension d){
        return as.getDiceNodes().get(d);
    }


}
