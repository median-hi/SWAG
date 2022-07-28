package at.jku.dke.swag.analysis_graphs.operations;

import at.jku.dke.swag.MDElems.Dimension;
import at.jku.dke.swag.MDElems.Level;

import java.util.List;
import java.util.Set;

public class OperationsInit {

    public static Set<Operation> initOperations(){
        Operation op = new Operation(DrillDownTo.getInstance(),
                List.of(new Dimension("destinationDim"),
                        new Level("geo")));

        return Set.of(op);
    }
}
