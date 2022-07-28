package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.asm_elements.Location;

import java.util.HashMap;
import java.util.Map;

public class SituationBinding {

    private Location location;
    private Map<Parameter, Constant> bindings = new HashMap<>();

    private SituationBinding(Location location) {
        this.location = location;
    }

    private SituationBinding(Location location, Map<Parameter, Constant> bindings) {
        this.location = location;
        this.bindings = bindings;
    }

    public SituationBinding create(Location location){
        return new SituationBinding(location);
    }

    public Location getLocation() {
        return location;
    }

    public SituationBinding setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Map<Parameter, Constant> getBindings() {
        return bindings;
    }

    public SituationBinding setBindings(Map<Parameter, Constant> bindings) {
        this.bindings = bindings;
        return this;
    }
}
