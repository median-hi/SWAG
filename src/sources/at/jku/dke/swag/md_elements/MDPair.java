package at.jku.dke.swag.md_elements;

import java.util.Objects;

public class MDPair {

    MDElement from;
    MDElement to;

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    public MDPair(MDElement from, MDElement to) {
        this.from = from;
        this.to = to;
    }

    public void setFrom(MDElement from) {
        this.from = from;
    }

    public void setTo(MDElement to) {
        this.to = to;
    }

    public MDElement getFrom() {
        return from;
    }

    public MDElement getTo() {
        return to;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MDPair mdPair = (MDPair) o;
        return from.equals(mdPair.from) && to.equals(mdPair.to);
    }

}
