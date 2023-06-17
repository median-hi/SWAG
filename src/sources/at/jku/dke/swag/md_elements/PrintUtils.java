package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.md_data.MDData;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrintUtils {
    public static void printSetOfLists(Set<List<String>> toPrint) {
        for (List<String> list : toPrint) {
            printCollectionInOneRow(list);
            System.out.println();
        }
    }

    public static void printMap(Map<List<String>, Set<String>> toPrint) {

        for (List<String> key : toPrint.keySet()) {
            printCollectionInOneRow(key);
            System.out.print(" FACTS: ");
            printCollectionInOneRow(toPrint.get(key));
            System.out.println();
        }
    }

    public static void printMapAndMultiSet(Map<List<String>, Set<String>> toPrint,
                                           MDGraph graph, MDData data, Measure measure) {

        for (List<String> key : toPrint.keySet()) {
            printCollectionInOneRow(key);
            System.out.print(" FACTS: ");
            printCollectionInOneRow(toPrint.get(key));
            printCollectionInOneRow(MDGraphUtils.getMultiSetOfMeasureValsForMultipleFacts(graph, data, toPrint.get(key), measure));
            System.out.println();
        }
    }

    public static void printMap1(Map<String, Double> map) {
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -- " + entry.getValue());
        }
    }

    public static void printMap2(Map<List<String>, Double> map) {
        for (Map.Entry<List<String>, Double> entry : map.entrySet()) {
            printCollectionInOneRow(entry.getKey());
            System.out.println(" -- " + entry.getValue());
        }
    }

    public static void printCollectionInOneRow(Collection<String> list) {

        for (String str : list) {
            System.out.print(str + " -- ");
        }
    }
}
