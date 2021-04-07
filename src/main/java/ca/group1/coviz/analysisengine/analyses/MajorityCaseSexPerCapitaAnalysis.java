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
 * An analysis to see whether male or female cases are more prevalent in a given country
 * 0 if all cases are female, 1 if all cases are male, 0.5 if equal
 * result can be anywhere in between
 * a method of rendering can be size of circle = abs(x - 0.5) and colour = x<0.5?pink:blue
 *
 * No need for asynchronisity here since there is only 1 api call
 */
public class MajorityCaseSexPerCapitaAnalysis implements IAnalysis {

    List<Country>targets;


    /**
     * Run the analysis
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    @Override
    public AnalysisResult analyze() throws InterruptedException, ExecutionException, IOException {
        CountryCaseAPIGetter casesInfo = new CountryCaseAPIGetter(targets);

        Map<Country, Map<String, Double>> rawData = new HashMap<>();

        Map<Country, Double> results = new HashMap<>();

        for(Country c : targets){
            Map<String, Double> countryRawData = new LinkedHashMap<>();
            Double maleCases = (Double)(double)casesInfo.getMaleCases().get(c);
            Double totalCases = (Double)(double)casesInfo.getCases().get(c);

            // 0.5 if male cases are half
            // 0 if no male cases
            // 1 if all male cases
            // 1 - the value is female cases
            Double portion = maleCases / totalCases;
            countryRawData.put("Male Cases", maleCases);

            // Female Cases metric was not used in the calculation but may be useful as raw data
            countryRawData.put("Female Cases", (double)casesInfo.getFemaleCases().get(c));
            countryRawData.put("Total Cases", totalCases);
            countryRawData.put("Male cases : Female cases", portion);


            results.put(c, portion);
            rawData.put(c, countryRawData);
        }
        return new AnalysisResult(AnalysisType.MAJORITY_CASE_SEX_PER_CAPITA, rawData, results);
    }



    public MajorityCaseSexPerCapitaAnalysis(List<Country> targets) {
        this.targets = targets;
    }

}
