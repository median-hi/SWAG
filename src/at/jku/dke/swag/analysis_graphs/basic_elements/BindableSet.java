package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.Copiable;
import at.jku.dke.swag.analysis_graphs.asm_elements.LocationValue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BindableSet implements LocationValue, Copiable {

    public static BindableSet empty(){return new BindableSet();}
    private Set<PairOrConstant> elements = new HashSet<>();

    public Set<ConstantOrUnknown> consts(){
        return elements.stream()
                        .filter(elem -> elem instanceof ConstantOrUnknown)
                        .map(elem -> (ConstantOrUnknown)elem)
                        .collect(Collectors.toSet());
    }

    public List<ConstantOrUnknown> constsList(){
        return elements.stream()
                .filter(elem -> elem instanceof ConstantOrUnknown)
                .map(elem -> (ConstantOrUnknown)elem)
                .collect(Collectors.toList());
    }

    public Set<Parameter> paras(){
        return elements.stream()
                .filter(elem -> elem instanceof Pair)
                .map(elem -> ((Pair)elem).getParameter())
                .collect(Collectors.toSet());
    }

    public List<Parameter> parasList(){
        return elements.stream()
                .filter(elem -> elem instanceof Pair)
                .map(elem -> ((Pair)elem).getParameter())
                .collect(Collectors.toList());
    }

    public void union(PairOrConstant elem){
        elements.add(elem);
        throwInValidExceptionIfInvalid();
    }

    public void setDifference(ConstantOrUnknown elem){
        elements.remove(elem);
        throwInValidExceptionIfInvalid();
    }

    public void setDifference(Parameter elem){
        Optional<Pair> pair = getPairOfParameter(elem);
        if(!pair.isEmpty()) {
            elements.remove(pair);
        }
        throwInValidExceptionIfInvalid();
    }

    public Optional<Pair> getPairOfParameter(Parameter param){
        return elements.stream()
                .filter(e -> e instanceof  Pair)
                .map(e -> ((Pair)e))
                .filter(e -> e.getParameter().equals(param)).findAny();
    }

    public boolean verifyValid(){
        return !hasDuplicates(constsList()) && !hasDuplicates(parasList());
    }

    public static <E> boolean hasDuplicates(List<E> list){
        Set<E> set = new HashSet<E>(list);
        return set.size() != list.size();
    }

    private void throwInValidExceptionIfInvalid(){
        if(!verifyValid()){
            throw new RuntimeException("Invalid bindable set");
        }
    }

    public boolean isPairOrConstant(){
        return false;
    }

    public boolean isBindableSet(){
        return true;
    }

    @Override
    public BindableSet copy() {
        BindableSet newSet = new BindableSet();

        for(PairOrConstant elm : this.getElements()){
            if(elm.isConstantOrUnknown()){
                newSet.getElements().add(elm);
            }
            if(elm.isPair()){
                newSet.getElements().add(((Pair)elm).copy());
            }
        }

        return newSet;
    }

    public Set<PairOrConstant> getElements() {
        return elements;
    }

    public void setElements(Set<PairOrConstant> elements) {
        this.elements = elements;
    }

}
