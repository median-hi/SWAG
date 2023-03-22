package at.jku.dke.swag.md_elements;

import java.util.*;

public class PrintUtils {

    public static void printSetOfLists(Set<List<String>> toPrint){
        for(List<String> list : toPrint){
            printCollectionInOneRow(list);
            System.out.println();
        }
    }

    public static void printMap(Map<List<String>, Set<String>> toPrint){

        for (List<String> key : toPrint.keySet()){
            printCollectionInOneRow(key);
            System.out.print(" FACTS: ");
            printCollectionInOneRow(toPrint.get(key));
            System.out.println();
        }
    }

    public static void printCollectionInOneRow(Collection<String> list) {

        for (String str : list){
            System.out.print(str + " -- ");
        }
    }
}
