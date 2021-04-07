package ca.group1.coviz.analysisengine.analyses;

import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.queryconstruction.CountryListBuilder;
import ca.group1.coviz.util.Country;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCasesPerCapitaAnalysis {
    @Test
    void testCasesPerCapitaCorrect(){
        Double EPSILON = 5E-3; // since sources other than the api give different results, give some leeway when comparing
        CountryListBuilder targets = CountryListBuilder.getInstance();

        assertTrue(targets.addCountry("Canada"));
        assertTrue(targets.addCountry("USA"));
        assertTrue(targets.addCountry("Italy"));
        assertTrue(targets.addCountry("Greece"));

        AnalysisResult analysis = null;
        try {
            analysis = new CasesPerCapitaAnalysis(targets.getList()).analyze();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        assertNotNull(analysis);

        Map<Country, Double> expected = new HashMap<>();

        expected.put(targets.getCountryByName("Canada"), 347466.0 / 37590000.0);
        expected.put(targets.getCountryByName("USA"), 9074939.0 / 328200000.0);
        expected.put(targets.getCountryByName("Italy"), 1178527.0 / 60360000.0);
        expected.put(targets.getCountryByName("Greece"), 112000.0 / 10720000.0);

        for(Country c : targets.getList()){
            Double statistic = analysis.getRenderableData().get(c);

            String message = String.format("%s: %f >= %f", c.getLongName(), statistic, expected.get(c));
            assertTrue(EPSILON >= Math.abs(expected.get(c) - statistic), message);
        }
    }
}
