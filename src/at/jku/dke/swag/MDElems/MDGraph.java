package at.jku.dke.swag.MDElems;

import java.util.*;
import java.util.stream.Collectors;

public class MDGraph {

    Set<Measure> M = new HashSet<>();
    Set<RollUpPair> LL = new HashSet<>();
    Set<Level>L = new HashSet<>();
    Set<Fact> F = new HashSet<>();
    Set<Dimension>D = new HashSet<>();
    Set<Hierarchy> H = new HashSet<>();


    Map<Dimension, Set<Hierarchy>> DH = new HashMap<>();

    Map<Hierarchy, Set<RollUpPair>> HLL = new HashMap<>();


    Map<Hierarchy, Set<Level>> HL = new HashMap<>();

    Map<Level, Set<LevelMember>> members = new HashMap<>();

    public Optional<Level> nextLevel(Hierarchy h, Level l){
        Set<RollUpPair> pairsToInspect =  LL
                .stream()
                .filter(pair -> pair.getFrom().equals(l))
                .collect(Collectors.toSet());

        return pairsToInspect
                .stream()
                .filter(pair -> isRollUpInHierarchy(h, pair))
                .map(pair -> pair.getTo())
                .findAny();
    }

    public boolean rollsUpTo(Level l1, Level l2){
        return LL.contains(new RollUpPair(l1, l2));
    }

    public boolean drillsDownTo(Level l1, Level l2){
        return rollsUpTo(l2, l1);
    }

    public Optional<Level> previousLevel(Hierarchy h, Level l){
        Set<RollUpPair> pairsToInspect =  LL
                .stream()
                .filter(pair -> pair.getTo().equals(l))
                .collect(Collectors.toSet());

        return pairsToInspect
                .stream()
                .filter(pair -> isRollUpInHierarchy(h, pair))
                .map(pair -> pair.getTo())
                .findAny();
    }

    public Level top(Dimension d){

        Set<Hierarchy> hierarchiesOfDim = getDH()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(d))
                .map(entry -> entry.getValue())
                .findAny()
                .orElse(Collections.emptySet());

        for (Hierarchy h : hierarchiesOfDim){
            Set<Level> levels= HL.get(h).stream().collect(Collectors.toSet());
            for(Level l : levels){
                System.out.println("hierarchy:" + h.getUri());
                if(LL.stream().noneMatch(pair -> l.equals(pair.getFrom()))){
                    return l;
                }
            }
        }

        return null;
    }

    public Level bot(Dimension d){

        Set<Hierarchy> hierarchiesOfDim = getDH()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(d))
                .map(entry -> entry.getValue())
                .findAny()
                .orElse(Collections.emptySet());

        for (Hierarchy h : hierarchiesOfDim){
            Set<Level> levels= HL.get(h).stream().collect(Collectors.toSet());
            for(Level l : levels){
                if(LL.stream().noneMatch(pair -> l.equals(pair.getTo()))){
                    return l;
                }
            }
        }

        return null;
    }

    public boolean isMemberOf(LevelMember member, Level level){
        return members.get(level).contains(member);
    }

    public boolean isRollUpInHierarchy(Hierarchy h, RollUpPair pair){
        return HLL.get(h).contains(pair);
    }

    public Set<RollUpPair> getLL() {
        return LL;
    }

    public void setLL(Set<RollUpPair> LL) {
        this.LL = LL;
    }

    public Set<Level> getL() {
        return L;
    }

    public void setL(Set<Level> l) {
        L = l;
    }

    public Set<Fact> getF() {
        return F;
    }

    public void setF(Set<Fact> f) {
        F = f;
    }

    public Set<Dimension> getD() {
        return D;
    }

    public void setD(Set<Dimension> d) {
        D = d;
    }

    public Map<Hierarchy, Set<RollUpPair>> getHLL() {
        return HLL;
    }

    public void setHLL(Map<Hierarchy, Set<RollUpPair>> HLL) {
        this.HLL = HLL;
    }

    public Map<Hierarchy, Set<Level>> getHL() {
        return HL;
    }

    public void setHL(Map<Hierarchy, Set<Level>> HL) {
        this.HL = HL;
    }


    public Set<Hierarchy> getH() {
        return H;
    }

    public void setH(Set<Hierarchy> h) {
        H = h;
    }


    public Map<Dimension, Set<Hierarchy>> getDH() {
        return DH;
    }

    public void setDH(Map<Dimension, Set<Hierarchy>> DH) {
        this.DH = DH;
    }


    public Set<Measure> getM() {
        return M;
    }

    public void setM(Set<Measure> m) {
        M = m;
    }

    public Map<Level, Set<LevelMember>> getMembers() {
        return members;
    }

    public void setMembers(Map<Level, Set<LevelMember>> members) {
        this.members = members;
    }

}
