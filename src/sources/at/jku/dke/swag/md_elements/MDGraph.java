package at.jku.dke.swag.md_elements;

import at.jku.dke.swag.analysis_graphs.basic_elements.Constant;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;

import java.util.*;
import java.util.stream.Collectors;

public class MDGraph {

    Set<Parameter> P = new HashSet<>();

    Set<Constant> C = new HashSet<>();

    Map<Parameter, Set<Constant>> DOM = new HashMap<>();

    Set<Measure> M = new HashSet<>();
    Set<RollUpPair> LL = new HashSet<>();
    Set<Level> L = new HashSet<>();

    Set<LevelAttribute> A = new HashSet<>();
    Set<Fact> F = new HashSet<>();
    Set<Dimension> D = new HashSet<>();
    Set<Hierarchy> H = new HashSet<>();

    Map<Dimension, Set<Hierarchy>> DH = new HashMap<>();

    Map<Hierarchy, Set<RollUpPair>> HLL = new HashMap<>();

    Map<Hierarchy, Set<Level>> HL = new HashMap<>();

    Map<Level, Set<LevelAttribute>> LA = new HashMap<>();

    Map<Fact, Set<Level>> FL = new HashMap<>();
    Map<Level, TreeSet<LevelMember>> members = new HashMap<>();
    /**
     * Map condition/measure/filter to its MD elements
     */
    Map<Constant, MDElement> mdElemes = new HashMap<>();
    /**
     * Map condition/measure/filter to its expression
     */
    Map<Constant, String> expressions = new HashMap<>();
    Map<Map.Entry<Dimension, RollUpPair>, String> rollUpProperties = new HashMap<>();


    public Set<Level> getLevelsOfDimension(Dimension d) {
        return DH.get(d).stream().flatMap(h -> HL.get(h).stream()).collect(Collectors.toSet());
    }

    public Set<LevelMember> getMembersOf(Level l) {
        if (members.get(l) == null)
            return new HashSet<>();
        return members.get(l);
    }

    public Set<LevelMember> getMembersOf(Dimension d) {
        return getLevelsOfDimension(d).stream().flatMap(l -> getMembersOf(l).stream()).collect(Collectors.toSet());
    }

    public Optional<Level> nextLevel(Hierarchy h, Level l) {
        Set<RollUpPair> pairsToInspect = LL
                .stream()
                .filter(pair -> pair.getFrom().equals(l))
                .collect(Collectors.toSet());

        return pairsToInspect
                .stream()
                .filter(pair -> isRollUpInHierarchy(h, pair))
                .map(pair -> pair.getTo())
                .findAny();
    }

    public LevelMember lastMember(Level l) {
        return members.get(l).last();
    }

    public LevelMember prevMember(Level l, LevelMember m) {
        return members.get(l).lower(m);
    }

    public LevelMember firstMember(Level l) {
        return members.get(l).first();
    }

    public LevelMember nextMember(Level l, LevelMember m) {
        return members.get(l).contains(m) ? members.get(l).higher(m) : null;
    }

    public boolean drillsDownTo(Hierarchy h, Level l1, Level l2) {
        Set<Level> preLevels = new HashSet<>();

        Level currLevel = l1;

        while (previousLevel(h, l1).isPresent()) {
            preLevels.add(previousLevel(h, l1).get());
            currLevel = previousLevel(h, l1).get();
        }

        return preLevels.contains(l2);
    }

    public boolean isLevelInHierarchy(Hierarchy h, Level l) {
        return getHL().get(h).contains(l);
    }


    public boolean drillsDownToInDimension(Dimension d, Level l1, Level l2) {

        for (Hierarchy h : getDH().get(d)) {

            Set<Level> preLevels = new HashSet<>();

            Level currLevel = l1;

            while (previousLevel(h, currLevel).isPresent()) {
                preLevels.add(previousLevel(h, currLevel).get());
                currLevel = previousLevel(h, currLevel).get();
            }

            if (preLevels.contains(l2)) {
                return true;
            }
        }

        return false;
    }

    public boolean rollsUpTo(Level l1, Level l2) {
        return LL.contains(new RollUpPair(l1, l2));
    }

    public boolean drillsDownTo(Level l1, Level l2) {
        return rollsUpTo(l2, l1);
    }

    public Optional<Level> previousLevel(Hierarchy h, Level l) {
        Set<RollUpPair> pairsToInspect = LL
                .stream()
                .filter(pair -> pair.getTo().equals(l))
                .collect(Collectors.toSet());

        return pairsToInspect
                .stream()
                .filter(pair -> isRollUpInHierarchy(h, pair))
                .map(RollUpPair::getFrom)
                .findAny();
    }

    public Level top(Dimension d) {

        Set<Hierarchy> hierarchiesOfDim = getDH()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(d))
                .map(entry -> entry.getValue())
                .findAny()
                .orElse(Collections.emptySet());

        for (Hierarchy h : hierarchiesOfDim) {
            Set<Level> levels = HL.get(h).stream().collect(Collectors.toSet());
            for (Level l : levels) {
                if (LL.stream().noneMatch(pair -> l.equals(pair.getFrom()))) {
                    return l;
                }
            }
        }

        return null;
    }

    public Level bot(Dimension d) {

        Set<Hierarchy> hierarchiesOfDim = getDH()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(d))
                .map(entry -> entry.getValue())
                .findAny()
                .orElse(Collections.emptySet());

        for (Hierarchy h : hierarchiesOfDim) {
            Set<Level> levels = HL.get(h).stream().collect(Collectors.toSet());
            for (Level l : levels) {
                if (LL.stream().noneMatch(pair -> l.equals(pair.getTo()))) {
                    return l;
                }
            }
        }

        return null;
    }

    public Dimension findFirstDimensionOfLevel(Level level) {
        for (Dimension d : this.getD()) {
            if (this.getLevelsOfDimension(d).contains(level)) {
                return d;
            }
        }
        return null;
    }

    public Level getNextRollUpLevel(Level level, Hierarchy hier) {
        for (RollUpPair ll : this.getHLL().get(hier)) {
            if (ll.getFrom().equals(level)) {
                return ll.getTo();
            }
        }
        return null;
    }

    public Level getFirstLevelOfAttribute(Dimension i, LevelAttribute l) {
        return LA.entrySet().stream().filter(e -> {
            return e.getValue().contains(l);
        }).findAny().get().getKey();
    }

    public boolean isMemberOf(LevelMember member, Level level) {
        return members.get(level).contains(member);
    }

    public boolean isRollUpInHierarchy(Hierarchy h, RollUpPair pair) {
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

    public Fact getFact() {
        return F.stream().findAny().orElseThrow();
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

    public Map<Level, TreeSet<LevelMember>> getMembers() {
        return members;
    }

    public void setMembers(Map<Level, TreeSet<LevelMember>> members) {
        this.members = members;
    }

    public Set<Parameter> getP() {
        return P;
    }

    public void setP(Set<Parameter> p) {
        P = p;
    }

    public Set<Constant> getC() {
        return C;
    }

    public void setC(Set<Constant> c) {
        C = c;
    }

    public Map<Parameter, Set<Constant>> getDOM() {
        return DOM;
    }

    public void setDOM(Map<Parameter, Set<Constant>> DOM) {
        this.DOM = DOM;
    }


    public Map<Constant, MDElement> getMdElemes() {
        return mdElemes;
    }

    public void setMdElemes(Map<Constant, MDElement> mdElemes) {
        this.mdElemes = mdElemes;
    }

    public Map<Constant, String> getExpressions() {
        return expressions;
    }

    public void setExpressions(Map<Constant, String> expressions) {
        this.expressions = expressions;
    }

    public Set<LevelAttribute> getA() {
        return A;
    }

    public void setA(Set<LevelAttribute> a) {
        A = a;
    }

    public Map<Level, Set<LevelAttribute>> getLA() {
        return LA;
    }

    public void setLA(Map<Level, Set<LevelAttribute>> LA) {
        this.LA = LA;
    }

    public Map<Fact, Set<Level>> getFL() {
        return FL;
    }

    public void setFL(Map<Fact, Set<Level>> FL) {
        this.FL = FL;
    }

    public Map<Map.Entry<Dimension, RollUpPair>, String> getRollUpProperties() {
        return rollUpProperties;
    }

    public void setRollUpProperties(Map<Map.Entry<Dimension, RollUpPair>, String> rollUpProperties) {
        this.rollUpProperties = rollUpProperties;
    }

}
