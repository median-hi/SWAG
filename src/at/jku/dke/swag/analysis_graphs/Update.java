package at.jku.dke.swag.analysis_graphs;

import java.util.Objects;

public class Update {

    Location location;
    LocationValue value;

    public Update(Location location, LocationValue value) {
        this.location = location;
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Update update = (Update) o;
        return Objects.equals(location, update.location) && Objects.equals(value, update.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, value);
    }

    public LocationValue getValue() {
        return value;
    }

    public void setValue(LocationValue value) {
        this.value = value;
    }
}
