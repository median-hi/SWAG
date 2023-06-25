package at.jku.dke.swag.analysis_graphs;

import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AnalysisGraphsTest {

    MDGraph mdGraph = MDGraphInit.initMDGraph();

    @Nested
    public class Test1 {
        @Test
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
    }
}
