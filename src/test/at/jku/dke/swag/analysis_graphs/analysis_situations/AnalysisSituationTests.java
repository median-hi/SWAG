package at.jku.dke.swag.analysis_graphs.analysis_situations;

import at.jku.dke.swag.AppConstants;
import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.SwagInit;
import at.jku.dke.swag.analysis_graphs.asm_elements.Location;
import at.jku.dke.swag.analysis_graphs.basic_elements.BindableSet;
import at.jku.dke.swag.analysis_graphs.basic_elements.Pair;
import at.jku.dke.swag.analysis_graphs.basic_elements.SituationBinding;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.MDGraph;
import at.jku.dke.swag.md_elements.init.MDGraphInit;
import at.jku.dke.swag.sparql.AsSparqlGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class AnalysisSituationTests {
    MDGraph mdGraph = MDGraphInit.initMDGraph();

    public AnalysisSituation createAs3Prime() {

        AnalysisSituation as = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);

        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setGran(AppConstants.TIME_DIM, AppConstants.YEAR);

        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));

        BindableSet selections = new BindableSet();
        selections.union(new Pair(AppConstants.D_PRED, AppConstants.YEAR_AFTER_2010));
        as.setDimensionSelection(AppConstants.TIME_DIM, selections);

        return as;
    }

    public AnalysisSituation createAs4Prime() {
        AnalysisSituation as = new AnalysisSituation(mdGraph);

        BindableSet measures = new BindableSet();
        measures.union(mdGraph.getM().stream().findAny().get());
        as.setMeasures(measures);

        as.setGran(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setGran(AppConstants.TIME_DIM, AppConstants.REF_PERIOD);

        as.setDiceLevel(AppConstants.DESTINATION_DIM, AppConstants.GEO);
        as.setDiceNode(AppConstants.DESTINATION_DIM, new Pair(AppConstants.GEO_NODE, AppConstants.GERMANY));

        BindableSet selections = new BindableSet();
        selections.union(new Pair(AppConstants.D_PRED, AppConstants.YEAR_AFTER_2010));
        as.setDimensionSelection(AppConstants.TIME_DIM, selections);

        BindableSet filters = new BindableSet();
        filters.union(new Pair(AppConstants.M_PRED, AppConstants.INTENSITY_GT_30K));
        as.setResultFilter(filters);

        return as;
    }

    public AnalysisSituation createAs3PrimeByBinding() {

        AnalysisSituation as = SwagInit.createAs3();

        SituationBinding binding1 = SituationBinding
                .create(Location.diceNodeOf(AppConstants.DESTINATION_DIM))
                .setBindings(
                        Map.of(AppConstants.GEO_NODE,
                                AppConstants.GERMANY));

        SituationBinding binding2 = SituationBinding
                .create(Location.selectoinOf(AppConstants.TIME_DIM))
                .setBindings(
                        Map.of(AppConstants.D_PRED,
                                AppConstants.YEAR_AFTER_2010));

        Set<SituationBinding> bindings = Set.of(binding1, binding2);
        return Utils.bind(as, bindings);
    }

    /**
     * Create AS4' by binding AS4
     *
     * @return AS4'
     */
    public AnalysisSituation createAs4PrimeByBinding() {

        AnalysisSituation as = SwagInit.createAs4();

        SituationBinding binding1 = SituationBinding
                .create(Location.diceNodeOf(AppConstants.DESTINATION_DIM))
                .setBindings(
                        Map.of(AppConstants.GEO_NODE,
                                AppConstants.GERMANY));

        SituationBinding binding2 = SituationBinding
                .create(Location.selectoinOf(AppConstants.TIME_DIM))
                .setBindings(
                        Map.of(AppConstants.D_PRED,
                                AppConstants.YEAR_AFTER_2010));

        SituationBinding binding3 = SituationBinding
                .create(Location.resultFilter())
                .setBindings(
                        Map.of(AppConstants.M_PRED,
                                AppConstants.INTENSITY_GT_30K));

        Set<SituationBinding> bindings = Set.of(binding1, binding2, binding3);
        return Utils.bind(as, bindings);
    }

    public AnalysisSituation createAs4PrimeWithWrongBindings() {

        AnalysisSituation as = SwagInit.createAs4();

        SituationBinding binding1 = SituationBinding
                .create(Location.diceNodeOf(AppConstants.DESTINATION_DIM))
                .setBindings(
                        Map.of(AppConstants.GEO_NODE,
                                AppConstants.EU));

        SituationBinding binding2 = SituationBinding
                .create(Location.selectoinOf(AppConstants.TIME_DIM))
                .setBindings(
                        Map.of(AppConstants.D_PRED,
                                AppConstants.YEAR_AFTER_2010));

        SituationBinding binding3 = SituationBinding
                .create(Location.resultFilter())
                .setBindings(
                        Map.of(AppConstants.M_PRED,
                                AppConstants.INTENSITY_GT_30K));

        Set<SituationBinding> bindings = Set.of(binding1, binding2, binding3);
        return Utils.bind(as, bindings);
    }

    @Nested
    @DisplayName("When binding situations")
    class BindingTest {

        @Test
        @DisplayName("Binding situations generates the expected situations")
        void bindingGeneratesExpectedSituations() {
            Assertions.assertEquals(createAs4Prime(), createAs4PrimeByBinding());
            Assertions.assertEquals(createAs3Prime(), createAs3PrimeByBinding());
        }

        @Test
        @DisplayName("Binding situations with wrong values results in errors")
        void wrongBindingsGenerateErrors() {
            Assertions.assertThrows(Exception.class, AnalysisSituationTests.this::createAs4PrimeWithWrongBindings);
        }
    }

    @Nested
    @DisplayName("Query generation")
    class QueryGenerationTest {
        @Test
        void testQueryGeneration() {
            AnalysisSituation as3P = createAs3PrimeByBinding();
            AsSparqlGenerator sparqlGenerator = new AsSparqlGenerator(mdGraph, as3P);
            sparqlGenerator.generateQuery();
        }
    }
}
