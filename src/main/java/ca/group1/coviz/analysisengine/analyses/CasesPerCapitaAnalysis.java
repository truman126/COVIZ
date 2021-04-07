package ca.group1.coviz.analysisengine.analyses;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.analysisengine.apiutils.IAnalysis;
import ca.group1.coviz.analysisengine.apigetters.CountryCaseAPIGetter;
import ca.group1.coviz.analysisengine.apigetters.CountryPopulationAPIGetter;
import ca.group1.coviz.util.Country;

/**
 * An analysis that gets the number of cases per person for the target countries
 */
public class CasesPerCapitaAnalysis implements IAnalysis {
    // this analysis only uses 2 threads because it needs 2 api calls
    List<Country> targets;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public CasesPerCapitaAnalysis(List<Country> targets) {
        this.targets = targets;
    }

    /**
     * Runs the analysis
     * @return The result of the analysis
     * @throws InterruptedException If the future in charge of getting the information from the API was interrupted
     * @throws ExecutionException If the future in charge of getting the information from the API ran into a problem
     */
    @Override
    public AnalysisResult analyze() throws InterruptedException, ExecutionException {
        Future<Map<Country, Integer>> casesFuture = executorService.submit(() -> new CountryCaseAPIGetter(targets).getCases());

        Future<Map<Country, Integer>> populationFuture = executorService.submit(() -> new CountryPopulationAPIGetter(targets).getPopulation());
        // blocks until all futures are completed
        executorService.shutdown();

        // the data for use in printing
        Map<Country, Map<String, Double>> raw_data = new HashMap<>();

        // map of cases in each country
        Map<Country, Integer> cases = casesFuture.get();

        // map of population in each country
        Map<Country, Integer> population = populationFuture.get();

        // the output of analyze() that can be used for rendering circles on a map
        Map<Country, Double> output = new HashMap<>();

        for(Country c : targets){
            // the raw data about this individual country, used for printing the info retrieved about it
            // linked hashmap retains order based on insertion, which allows me to guarantee the order in which
            // the entries are displayed
            Map<String, Double> country_raw_data = new LinkedHashMap<>();

            // the actual statistical  analysis function
            Double casesPerCapita = (Double)(double)cases.get(c) / (Double)(double)population.get(c);

            // store the raw info for later display
            country_raw_data.put("Cases", (double)cases.get(c));
            country_raw_data.put("Population", (double)population.get(c));
            country_raw_data.put("Cases per capita", casesPerCapita);
            raw_data.put(c, country_raw_data);
            output.put(c, casesPerCapita);
        }
        return new AnalysisResult(AnalysisType.CASES_PER_CAPITA, raw_data, output);
    }

}
