package at.jku.dke.swag.md_elements;

import java.util.HashMap;
import java.util.Map;

public class MappedMDGraph {

    Map<MDElement, String> mdElmsMappings = new HashMap<>();
    Map<MDPair, String> mdPairsMappings = new HashMap<>();

    public void add(MDElement elm, String str){
        mdElmsMappings.put(elm, str);
    }

    public void add(MDElement from, MDElement to, String str){
        mdPairsMappings.put(new MDPair(from, to), str);
    }

    public String get(MDElement elm){
        return mdElmsMappings.get(elm);
    }

    public String get(MDElement from, MDElement to){
        return mdElmsMappings.get(new MDPair(from, to));
    }
}
