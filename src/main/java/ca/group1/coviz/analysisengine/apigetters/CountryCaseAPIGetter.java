package ca.group1.coviz.analysisengine.apigetters;

import ca.group1.coviz.analysisengine.apiutils.GlobalHealth5050URL;
import ca.group1.coviz.analysisengine.apiutils.JSONAPIInformationGetter;
import ca.group1.coviz.queryconstruction.CountryListBuilder;
import ca.group1.coviz.util.Country;
import okhttp3.Request;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

/**
 * Gets the cases in the target countries
 */
public class CountryCaseAPIGetter extends JSONAPIInformationGetter {
    private List<Country> targets;
    private Map<Country, Integer> maleCases;
    private Map<Country, Integer> femaleCases;
    private Map<Country, Integer> combinedCases;
    private boolean resultRetrieved = false;

    public CountryCaseAPIGetter(List<Country> targets){
        this.targets = targets;
    }

    /**
     * Gets the male cases by country
     * @return The number of male cases in each country
     * @throws IOException If there was an error parsing the API response
     */
    public Map<Country, Integer>  getMaleCases() throws IOException {
        if(!resultRetrieved){
            this.getAndParseData();
        }
        return maleCases;
    }

    /**
     * Gets the female cases by country
     * @return The number of female cases in each country
     * @throws IOException If there was an error parsing the API response
     */
    public Map<Country, Integer> getFemaleCases() throws IOException {
        if(!resultRetrieved){
            this.getAndParseData();
        }
        return femaleCases;
    }

    /**
     * Gets the number of total cases by country
     * @return The number of cases in each country
     * @throws IOException If there was an error parsing the API response
     */
    public Map<Country, Integer> getCases() throws IOException {
        if(!resultRetrieved) {
            this.getAndParseData();
        }
        return combinedCases;
    }

    /**
     * Gets the Data from the API and retrieves the info from it
     * @throws IOException If there was an issue parsing the JSON
     */
    private void getAndParseData() throws IOException {
        if(this.resultRetrieved){ // additional calls have no effect because we've already retrieved data
            return;
        }

        JSONObject data = this.getJSONData().getJSONObject("data");
        maleCases = new HashMap<>();
        femaleCases = new HashMap<>();
        combinedCases = new HashMap<>();
        for(String countryLongName : data.keySet()){
            JSONObject countryInfo = data.getJSONObject(countryLongName);
            Country outputCountry = CountryListBuilder.getInstance().getCountryByName(countryLongName);
            // yes, the API returns a JSON where deaths is a string
            String countryMaleCasesString = countryInfo.getString("cases_male");
            Integer countryMaleCases = null;
            if(countryMaleCasesString != null && !countryMaleCasesString.equals("")){
                countryMaleCases = Integer.parseInt(countryMaleCasesString);
            }
            else{
                countryMaleCases = -1;
            }

            String countryFemaleCasesString = countryInfo.getString("cases_female");
            Integer countryFemaleCases = null;
            if(countryFemaleCasesString != null && !countryFemaleCasesString.equals("")){
                countryFemaleCases = Integer.parseInt(countryFemaleCasesString);
            }else{
                countryFemaleCases = -1;
            }

            String countryCombinedCasesString = countryInfo.getString("cases_total_sum");
            Integer countryCombinedCases = null;
            if(countryCombinedCasesString != null && !countryCombinedCasesString.equals("")){
                countryCombinedCases = Integer.parseInt(countryCombinedCasesString);
            }else{
                countryCombinedCases = -1;
            }

            maleCases.put(outputCountry, countryMaleCases);
            femaleCases.put(outputCountry, countryFemaleCases);
            combinedCases.put(outputCountry, countryCombinedCases);
        }
        resultRetrieved = true;
    }

    @Override
    public Request getDataURL() {
        return GlobalHealth5050URL.summaryURL(this.targets);
    }

}
