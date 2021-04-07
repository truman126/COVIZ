package ca.group1.coviz.analysisengine.analyses;

import java.io.IOException;
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
import ca.group1.coviz.analysisengine.apigetters.CountryDeathAPIGetter;
import ca.group1.coviz.analysisengine.apigetters.CountryPopulationAPIGetter;
import ca.group1.coviz.util.Country;

/**
 * An Analysis that calculates the number of deaths per capita in the selected Countries
 */
public class DeathsPerCapitaAnalysis implements IAnalysis {
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private List<Country> targets;

    /**
     * Runs the analysis on the target countries
     * @return An analysis result for the targets
     * @throws InterruptedException If a Future was interrupted while trying to request web info
     * @throws ExecutionException If there was an exception running the Futures
     */
    @Override
    public AnalysisResult analyze() throws InterruptedException, ExecutionException {
        Future<Map<Country, Integer>> countryDeathsFuture = executorService.submit(()-> new CountryDeathAPIGetter(targets).getCombinedDeaths());

        Future<Map<Country, Integer>> countryPopulationFuture = executorService.submit(()->new CountryPopulationAPIGetter(targets).getPopulation());

        executorService.shutdown();

        Map<Country, Map<String, Double>> rawData = new HashMap<>();

        Map<Country, Integer> countryDeaths=  countryDeathsFuture.get();

        Map<Country, Integer> countryPopulation = countryPopulationFuture.get();

        Map<Country, Double> output = new HashMap<>();

        for(Country c : targets){
            Map<String, Double> countryRawData = new LinkedHashMap<>();
            Double deaths = (Double)(double)countryDeaths.get(c);
            Double population = (Double)(double)countryPopulation.get(c);

            countryRawData.put("Population", population);
            countryRawData.put("Deaths", deaths);
            countryRawData.put("Deaths per capita", deaths/population);
            output.put(c, deaths/population);
            rawData.put(c, countryRawData);
        }
        return new AnalysisResult(AnalysisType.DEATHS_PER_CAPITA, rawData, output);
    }

    public DeathsPerCapitaAnalysis(List<Country> targets) {
        this.targets = targets;
    }

}
