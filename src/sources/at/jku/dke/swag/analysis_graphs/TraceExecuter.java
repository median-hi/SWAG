package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.analysis_graphs.basic_elements.OperationBinding;
import at.jku.dke.swag.analysis_graphs.basic_elements.SituationBinding;
import at.jku.dke.swag.analysis_graphs.operations.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TraceExecuter {

    void execute() {

        Trace trace = new Trace();

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Initial Situation index");

        Integer index = Integer.valueOf(myObj.nextLine());  // Read user input


        System.out.println("Username is: " + userName);  // Output user input
    }

    class ETrace {

        List<AnalysisSituation> situations = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        SituationBinding initialAsBindings;
        List<Map<Operation, OperationBinding>> stepBindings = new ArrayList<>();
        ETrace() {
            super();
        }
    }

}
