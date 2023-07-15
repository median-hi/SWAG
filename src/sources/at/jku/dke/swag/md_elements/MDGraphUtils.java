package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.md_data.MDData;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public class MDGraphUtils {

    public static List<MDElement> makePath(MDGraph mdGraph, Level level) {

        List<MDElement> path = new LinkedList<>();

        Dimension dim = mdGraph.findFirstDimensionOfLevel(level);
        Set<Hierarchy> hiers = mdGraph.getDH().get(dim);

        for (Hierarchy hier : hiers) {

            if (mdGraph.getHL().get(hier).contains(level)) {

                path.add(mdGraph.getFact());

                if (level.equals(mdGraph.bot(dim))) {
                    path.add(level);
                    return path;
                }

                Level curLevel = mdGraph.bot(dim);

                while (curLevel != null) {
                    Level next = mdGraph.getNextRollUpLevel(curLevel, hier);
                    if (curLevel != null) {
                        path.add(curLevel);
                        if (curLevel.equals(level)) {
                            return path;
                        }
                        curLevel = next;
                    } else {
                        break;
                    }
                }
            }
        }
        return null;
    }

    public static List<RollUpPair> getRollUpPath(MDGraph mdGraph, Dimension dim, Level level1, Level level2) {

        List<RollUpPair> path = new LinkedList<>();

        if (level2.equals(mdGraph.bot(dim))) {
            return path;
        }

        Set<Hierarchy> hiers = mdGraph.getDH().get(dim);
        for (Hierarchy hier : hiers) {
            if (mdGraph.getHL().get(hier).contains(level2)) {
                Level curLevel = level1;
                while (curLevel != null) {
                    Level next = mdGraph.getNextRollUpLevel(curLevel, hier);
                    path.add(new RollUpPair(curLevel, next));
                    if (next.equals(level2)) {
                        return path;
                    }
                    curLevel = next;
                }
            }
        }
        return path;
    }

    public static Set<List<String>> getFactAndCoordinates(MDData data, MDGraph graph, List<Level> groupBy) {
        Set<List<String>> all = new HashSet<>();

        for (List<String> coordinate : getCoordinatesOfGroupBy(data, groupBy)) {
            for (String factInstance : getFactsInCoordinate(data, graph, groupBy, coordinate)) {
                List<String> l = new ArrayList<>();
                l.add(factInstance);
                l.addAll(coordinate);
                all.add(l);
            }
        }

        return all;
    }

    public static Multiset getMultiSetOfMeasureVals(MDGraph graph, MDData data, String fact, Measure measure) {
        Multiset multiset = HashMultiset.create();

        Set<String[]> instances = data.get(graph.getFact(), measure);
        Set<String[]> newInstances = new HashSet<>();

        for (String[] array : instances) {
            if (array[0].equals(fact)) {
                multiset.add(array[1]);
            }
        }
        return multiset;
    }

    public static Multiset getMultiSetOfMeasureValsForMultipleFacts(MDGraph graph, MDData data, Set<String> facts, Measure measure) {
        Multiset multiset = HashMultiset.create();

        for (String fact : facts) {
            multiset.addAll(getMultiSetOfMeasureVals(graph, data, fact, measure));
        }

        return multiset;
    }

    public static Map<List<String>, Set<String>> getFactAndCoordinatesAsSet(MDData data, MDGraph graph, List<Level> groupBy) {
        Map<List<String>, Set<String>> all = new HashMap<>();

        for (List<String> coordinate : getCoordinatesOfGroupBy(data, groupBy)) {
            all.put(coordinate, new HashSet<>());
            for (String factInstance : getFactsInCoordinate(data, graph, groupBy, coordinate)) {
                all.get(coordinate).add(factInstance);
            }
        }
        return all;
    }

    public static Map<List<String>, Set<String>> getFactAndCoordinatesAsSetDefaultValue(MDData data,
                                                                                        MDGraph graph,
                                                                                        List<Level> groupBy) {
        Map<List<String>, Set<String>> all = new HashMap<>();

        for (List<String> coordinate : getCoordinatesOfGroupBy(data, groupBy)) {
            all.put(coordinate, new HashSet<>());
            for (String factInstance : getFactsInCoordinate(data, graph, groupBy, coordinate)) {
                all.get(coordinate).add(factInstance);
            }
        }

        Set<String> allConsideredFacts = all.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
        Set<String> allFacts = data.get(graph.getFact());

        all.put(List.of("Default"), new HashSet<>());

        for (String fact : allFacts) {
            if (!allConsideredFacts.contains(fact)) {
                all.get(List.of("Default")).add(fact);
            }
        }
        return all;
    }


    public static void eliminate(Map<List<String>, Set<String>> map) {

        List<List<String>> list = new ArrayList<>();
        list = new ArrayList<>(map.keySet());

        List<List<String>> sortedList = list.stream().sorted((o1, o2) -> {
            for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
                int c = o1.get(i).compareTo(o2.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return Integer.compare(o1.size(), o2.size());
        }).collect(Collectors.toList());

        List<String> vals = map.values().stream().flatMap(set -> set.stream()).collect(Collectors.toList());

        for (String fact : vals) {
            List<List<String>> coordsOfFact = new ArrayList<>();
            for (Map.Entry<List<String>, Set<String>> entry : map.entrySet()) {
                if (entry.getValue().contains(fact)) {
                    coordsOfFact.add(entry.getKey());
                }
            }
            if (coordsOfFact.size() > 1) {
                List<Integer> indices = new ArrayList<>();
                sortedList.stream().filter(lst -> coordsOfFact.contains(lst))
                        .forEach(x -> {
                            indices.add(sortedList.indexOf(x));
                        });
                indices.remove(0);
                for (int i : indices) {
                    map.get(sortedList.get(i)).remove(fact);
                }
            }
        }

    }


    public static Map<List<String>, Set<String>> getFactAndCoordinatesAsSetTakeOne(MDData data, MDGraph graph, List<Level> groupBy) {
        Map<List<String>, Set<String>> all = new HashMap<>();

        for (List<String> coordinate : getCoordinatesOfGroupBy(data, groupBy)) {
            all.put(coordinate, new HashSet<>());
            for (String factInstance : getFactsInCoordinate(data, graph, groupBy, coordinate)) {
                all.get(coordinate).add(factInstance);
            }
        }
        eliminate(all);
        return all;
    }


    public static Set<List<String>> getCoordinatesOfGroupBy(MDData data, List<Level> groupBy) {

        List<Set<String>> storage = new ArrayList<>();
        for (Level l : groupBy) {
            storage.add(data.get(l));
        }
        return Sets.cartesianProduct(storage);
    }

    public static Set<String> getFactsInCoordinate(MDData data, MDGraph graph, List<Level> groupBy, List<String> coordinate) {

        Set<String> facts = new HashSet<>();

        for (String factInstance : data.get(graph.getFact())) {
            boolean isFactIn = true;

            for (int i = 0; i < groupBy.size() & isFactIn; i++) {
                List<MDElement> path = makePath(graph, groupBy.get(i));
                if (!isConnected(factInstance, coordinate.get(i), graph, data, path)) {
                    isFactIn = false;
                }
            }

            if (isFactIn) {
                facts.add(factInstance);
            }
        }
        return facts;
    }

    public static boolean isConnected_(String start, String end, MDGraph graph, MDData data, List<MDElement> path) {

        String currentNode = start;
        String[] currentEdge;

        List<String> connection = new ArrayList<>();

        MDElement prevElm = null;

        for (MDElement e : path) {
            if (prevElm != null) {
                for (String[] instance : data.get(prevElm, e)) {
                    if (instance[0].equals(currentNode)) {
                        connection.add(currentNode);
                        connection.add(instance[1]);
                    }
                }
            }
            for (String instance : data.get(e)) {
                if (instance.equals(currentNode)) {
                    connection.add(currentNode);
                }
            }
            prevElm = e;
        }
        return false;
    }

    public static Set<List<String>> getAllPaths(String start, String end, MDGraph graph, MDData data, List<MDElement> path) {

        List<Set<String>> allSetsToMultiply = new ArrayList<>();
        allSetsToMultiply.add(Set.of(start));

        for (int i = 1; i < path.size() - 1; i++) {
            allSetsToMultiply.add(data.get(path.get(i)));
        }

        allSetsToMultiply.add(Set.of(end));

        Set<List<String>> all = Sets.cartesianProduct(allSetsToMultiply);

        Set<List<String>> toRemove = new HashSet<>();

        for (int i = 0; i < path.size() - 1; i++) {
            for (List<String> list : all) {
                String[] conn = new String[]{list.get(i), list.get(i + 1)};
                if (!islistOfArrayContinas(data.get(path.get(i), path.get(i + 1)), conn)) {
                    toRemove.add(list);
                }
            }
        }


        Set<List<String>> newSet = new HashSet<>();
        for (List<String> list : all) {
            if (!toRemove.contains(list)) {
                newSet.add(list);
            }
        }

        return newSet;
    }

    public static boolean islistOfArrayContinas(Set<String[]> list, String[] array) {

        boolean notFound = false;
        for (String[] tempArray : list) {
            notFound = false;

            if (tempArray.length != array.length) {
                continue;
            }
            for (int i = 0; i < array.length && !notFound; i++) {
                if (!tempArray[i].equals(array[i])) {
                    notFound = true;
                    continue;
                }
                if (i == array.length - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getRollUpProperty(MDGraph md, Dimension i, RollUpPair p) {
        return md.getRollUpProperties().get(new AbstractMap.SimpleEntry<Dimension, RollUpPair>(i, p));
    }

    public static boolean isConnected(String start, String end, MDGraph graph, MDData data, List<MDElement> path) {
        return !getAllPaths(start, end, graph, data, path).isEmpty();
    }
}
