package at.jku.dke.swag.md_data;

import at.jku.dke.swag.md_elements.MDElement;
import at.jku.dke.swag.md_elements.MDPair;

import java.util.*;

public class MDData {

    Map<MDElement, Set<String>> mdElmsData = new HashMap<>();
    Map<MDPair, Set<String []>> mdPairsData = new HashMap<>();

    public void add(MDElement elm, Set<String> data){
        mdElmsData.put(elm, data);
    }

    public void add(MDElement from, MDElement to,  Set<String[]> data){
        mdPairsData.put(new MDPair(from, to), data);
    }

    public Set<String> get(MDElement elm){
        mdElmsData.putIfAbsent(elm, new HashSet<>());
        return mdElmsData.get(elm);
    }

    public Set<String[]>  get(MDElement from, MDElement to){
        mdPairsData.putIfAbsent(new MDPair(from, to), new HashSet<>());
        return mdPairsData.get(new MDPair(from, to));
    }
}
