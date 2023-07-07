package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.Copiable;
import at.jku.dke.swag.analysis_graphs.asm_elements.LocationValue;

import java.util.*;
import java.util.stream.Collectors;

public class BindableSet implements LocationValue, Copiable {

    private Set<PairOrConstant> elements = new HashSet<>();

    public BindableSet() {

    }

    public BindableSet(Set<PairOrConstant> elements) {
        this.elements = elements;
    }

    public static BindableSet empty() {
        return new BindableSet();
    }

    public static <E> boolean hasDuplicates(List<E> list) {
        Set<E> set = new HashSet<E>(list);
        return set.size() != list.size();
    }

    public Set<Constant> nbConsts() {
        return elements.stream()
                .filter(elem -> elem instanceof Constant)
                .map(elem -> (Constant) elem)
                .collect(Collectors.toSet());
    }

    public List<Constant> constsList() {
        return elements.stream()
                .filter(elem -> elem instanceof Constant)
                .map(elem -> (Constant) elem)
                .collect(Collectors.toList());
    }

    public Set<Parameter> paras() {
        return elements.stream()
                .filter(elem -> elem instanceof Pair)
                .map(elem -> ((Pair) elem).getParameter())
                .collect(Collectors.toSet());
    }

    public List<Parameter> parasList() {
        return elements.stream()
                .filter(elem -> elem instanceof Pair)
                .map(elem -> ((Pair) elem).getParameter())
                .collect(Collectors.toList());
    }

    public void union(PairOrConstant elem) {
        elements.add(elem);
        throwInValidExceptionIfInvalid();
    }

    public void setDifference(ConstantOrUnknown elem) {
        elements.remove(elem);
        throwInValidExceptionIfInvalid();
    }

    public void setDifference(Pair elem) {
        elements.remove(elem);
        throwInValidExceptionIfInvalid();
    }

    public void setDifference(Parameter elem) {
        Optional<Pair> pair = getPairOfParameter(elem);
        if (!pair.isEmpty()) {
            elements.remove(pair.get());
        }
        throwInValidExceptionIfInvalid();
    }

    public Optional<Pair> getPairOfParameter(Parameter param) {
        return elements.stream()
                .filter(e -> e instanceof Pair)
                .map(e -> ((Pair) e))
                .filter(e -> e.getParameter().equals(param)).findAny();
    }

    public boolean verifyValid() {
        return !hasDuplicates(parasList());
    }

    public void throwInValidExceptionIfInvalid() {
        if (!verifyValid()) {
            throw new RuntimeException("Invalid bindable set");
        }
    }

    public boolean isPairOrConstant() {
        return false;
    }

    public boolean isBindableSet() {
        return true;
    }

    @Override
    public BindableSet copy() {
        BindableSet newSet = new BindableSet();

        for (PairOrConstant elm : this.getElements()) {
            if (elm.isConstantOrUnknown()) {
                newSet.getElements().add(elm);
            }
            if (elm.isPair()) {
                newSet.getElements().add(((Pair) elm).copy());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BindableSet that = (BindableSet) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

}
