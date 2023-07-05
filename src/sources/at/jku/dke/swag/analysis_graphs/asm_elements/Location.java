package at.jku.dke.swag.analysis_graphs.asm_elements;

import at.jku.dke.swag.md_elements.Dimension;

import java.util.Objects;

public class Location {

    private String location;

    public Location(String location) {
        this.location = location;
    }

    public static Location resultFilter() {
        return new Location("filter");
    }

    public static Location granularityOf(Dimension dimension) {
        return new Location("gran_" + dimension.getUri());
    }

    public static Location diceLevelOf(Dimension dimension) {
        return new Location("level_" + dimension.getUri());
    }

    public static Location diceNodeOf(Dimension dimension) {
        return new Location("node_" + dimension.getUri());
    }

    public static Location selectoinOf(Dimension dimension) {
        return new Location("selection_" + dimension.getUri());
    }

    public Location measure() {
        return new Location("measure");
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isGranLocation() {
        return this.getLocation().startsWith("gran");
    }

    public boolean isDiceLevelLocation() {
        return this.getLocation().startsWith("level");
    }

    public boolean isDiceNodeLocation() {
        return this.getLocation().startsWith("node");
    }

    public boolean isDimensionSelectionLocation() {
        return this.getLocation().startsWith("selection");
    }

    public boolean isMeasureLocation() {
        return this.getLocation().startsWith("measure");
    }

    public boolean isResultFilterLocation() {
        return this.getLocation().startsWith("filter");
    }

    public boolean isDimensionUpdate() {
        return this.isGranLocation()
                || this.isDiceLevelLocation()
                || this.isDiceNodeLocation()
                || this.isDimensionSelectionLocation();
    }

    public String getDimensionOfUpdate() {
        String dim = "";
        if (this.isDimensionUpdate()) {
            dim = this.location.split("_")[1];
        }
        return dim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return Objects.equals(location, location1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
