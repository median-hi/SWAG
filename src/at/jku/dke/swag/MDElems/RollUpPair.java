package at.jku.dke.swag.MDElems;

import java.util.Objects;

public class RollUpPair {

    Level from;
    Level to;

    public RollUpPair(Level from, Level to) {
        this.from = from;
        this.to = to;
    }

    public Level getFrom() {
        return from;
    }

    public void setFrom(Level from) {
        this.from = from;
    }

    public Level getTo() {
        return to;
    }

    public void setTo(Level to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RollUpPair that = (RollUpPair) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
