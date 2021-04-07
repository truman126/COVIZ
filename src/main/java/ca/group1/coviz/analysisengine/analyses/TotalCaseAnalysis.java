package ca.group1.coviz.analysisengine.analyses;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.analysisengine.apiutils.IAnalysis;
import ca.group1.coviz.analysisengine.apigetters.CountryCaseAPIGetter;
import ca.group1.coviz.util.Country;

/**
 * Gets a measure for whether male cases are the majority or female cases are the majority
 */
public class TotalCaseAnalysis implements IAnalysis {
    /**
     * No need for asynchronisity because there is only 1 api call
     */
    List<Country> targets;

    public TotalCaseAnalysis(List<Country> targets) {
        this.targets = targets;
    }

    /**
     * Executes the analysis on the target countries
     * @return An analysis result
     * @throws InterruptedException If the information getter was interrupted (Not thrown by this implementor)
     * @throws ExecutionException If the information getter ran into an error (Not thrown by this implementor)
     * @throws IOException If there was an issue parsing the JSON API info
     */
    @Override
    public AnalysisResult analyze() throws InterruptedException, ExecutionException, IOException {

        Map<Country, Double> output = new HashMap<>();

        Map<Country, Map<String, Double>> rawData = new HashMap<>();

        // get the country info
        Map<Country, Integer> casesData = new CountryCaseAPIGetter(targets).getCases();

        for (Country c: targets) {
            Map<String, Double> countryRawData = new LinkedHashMap<>();

            Double cases = (double)casesData.get(c);

            output.put(c, cases);
            countryRawData.put("Cases", cases);
            rawData.put(c, countryRawData);
        }
        return new AnalysisResult(AnalysisType.TOTAL_CASES, rawData, output);
    }

}
