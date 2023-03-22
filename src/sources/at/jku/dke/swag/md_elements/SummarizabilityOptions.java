package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.md_data.MDData;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.*;

public class SummarizabilityOptions {

    private MDGraph graph;
    private MDData data;

    public SummarizabilityOptions(MDGraph graph, MDData data) {
        this.graph = graph;
        this.data = data;
    }

    public Map<String, Double> getSplitWeightPerGroup(Map<List<String>, Set<String>> coordinatesAndFacts){

        Map<String, Double> result = new HashMap<>();

        Map<String, Set<List<String>>> factAndItsCorrdinates = new HashMap<>();

        // collect facts
        Set<String> facts = new HashSet<>();
        for (Set<String> factsInMap : coordinatesAndFacts.values()){
            facts.addAll(factsInMap);
        }

        // Collect coordinates per fact
        for (List<String> coordinate : coordinatesAndFacts.keySet()){
            for (String fact: facts){
                if(coordinatesAndFacts.get(coordinate).contains(fact)){
                    if (factAndItsCorrdinates.get(fact) == null){
                        factAndItsCorrdinates.put(fact,new HashSet<>());
                    }
                    factAndItsCorrdinates.get(fact).add(coordinate);
                }
            }
        }

        for (Map.Entry<String, Set<List<String>>> entry : factAndItsCorrdinates.entrySet()){
            result.put(entry.getKey(), 1d / entry.getValue().size());
        }

        return result;
    }

    public  Map<List<String>, Multiset<Double>> getAggregateSplit(Map<List<String>, Set<String>> coordinatesAndFacts,
                                                                Map<String, Double> weights,
                                                               Measure measure){

      Map<List<String>, Multiset<Double>> aggregates = new HashMap<>();

      for (List<String> key : coordinatesAndFacts.keySet()){
          aggregates.put(key, HashMultiset.create());
          for(String fact : coordinatesAndFacts.get(key)){
              aggregates.get(key).addAll(getMeasuresOfFact(fact, measure,weights));
          }
      }
      return aggregates;
    }

    public  Map<List<String>, Double> aggregateSplit(Map<List<String>, Set<String>> coordinatesAndFacts,
                                                                  Map<String, Double> weights,
                                                                  Measure measure){
        Map<List<String>, Double> res = new HashMap<>();

        Map<List<String>, Multiset<Double>> aggs
                = getAggregateSplit(coordinatesAndFacts,
                 weights,
                 measure);

        for (List<String> key : aggs.keySet()){
            res.put(key, aggs.get(key).stream().reduce(0d, Double::sum));
        }

        return res;
    }

    public Set<Double> getMeasuresOfFact(String fact, Measure measure, Map<String, Double> weights){
        Set<Double> measures = new HashSet<>();

        Set<String[]> instances = data.get(graph.getFact(), measure);
        Set<String[]> newInstances = new HashSet<>();

        for (String [] array : instances){
            if (array[0].equals(fact)){
                measures.add(Double.valueOf(array[1]) * weights.get(fact));
            }
        }

        return measures;
    }
}
