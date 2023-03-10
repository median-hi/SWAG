package at.jku.dke.swag.analysis_graphs.basic_elements;

import at.jku.dke.swag.analysis_graphs.asm_elements.Location;

import java.util.HashMap;
import java.util.Map;

public class OperationBinding {


    private Map<Integer, Constant> bindings = new HashMap<>();

    private OperationBinding(Map<Integer, Constant> bindings) {
        this.bindings = bindings;
    }

    private OperationBinding() {
    }

    public static OperationBinding create(){
        return new OperationBinding();
    }


    public Map<Integer, Constant> getBindings() {
        return bindings;
    }

    public OperationBinding setBindings(Map<Integer, Constant> bindings) {
        this.bindings = bindings;
        return this;
    }
}
