package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AnalysisGraphsTest {

    MDGraph mdGraph = MDGraphInit.initMDGraph();

    @Test
    @DisplayName("The execution of a SWAG at the schema level results in the expected outcome.")
    public void testDissertationRunningExample() {

        List<AnalysisSituation> situations = SwagInit.initAg();
        List<AnalysisSituation> expectedSituations = new ArrayList<>();

        expectedSituations.add(SwagInit.createAs1());
        expectedSituations.add(SwagInit.createAs2());
        expectedSituations.add(SwagInit.createAs3());
        expectedSituations.add(SwagInit.createAs4());
        expectedSituations.add(SwagInit.createAs5());

        for (int i = 0; i < 5; i++) {
            System.out.println("i= " + i);
            Assertions.assertEquals(situations.get(i), expectedSituations.get(i));
        }
    }

    @Test
    @DisplayName("The execution of a SWAG at the schema level with an operation in a step producing an empty update set results in an exception.")
    public void testDissertationRunningExampleWithAnError() {

        List<AnalysisSituation> situations = SwagInit.initAg();

        List<AnalysisSituation> expectedSituations = new ArrayList<>();

        expectedSituations.add(SwagInit.createAs1());
        expectedSituations.add(SwagInit.createAs2());
        expectedSituations.add(SwagInit.createAs3());
        expectedSituations.add(SwagInit.createAs4());
        expectedSituations.add(SwagInit.createAs5());

        for (int i = 0; i < 5; i++) {
            System.out.println("i= " + i);
            Assertions.assertEquals(situations.get(i), expectedSituations.get(i));
        }
    }

}
