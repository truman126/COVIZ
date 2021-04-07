package ca.group1.coviz.analysisengine.apigetters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import ca.group1.coviz.analysisengine.apiutils.JSONAPIInformationGetter;
import ca.group1.coviz.analysisengine.apiutils.GlobalHealth5050URL;
import ca.group1.coviz.queryconstruction.CountryListBuilder;
import ca.group1.coviz.util.Country;
import okhttp3.Request;

/**
 * Gets the population of the target countries. Unit is individual people (API uses thousands of people as the unit)
 */
public class CountryPopulationAPIGetter extends JSONAPIInformationGetter {
    List<Country> targets;
    boolean retrievedData = false;
    Map<Country, Integer> malePopulation = new HashMap<>();
    Map<Country, Integer> femalePopulation = new HashMap<>();
    Map<Country, Integer> totalPopulation = new HashMap<>();

    public CountryPopulationAPIGetter(List<Country> targets) {
        this.targets = targets;
    }
    /**
     * Get the total populations of the target countries
     */
    public Map<Country, Integer> getPopulation() throws IOException {

        if(!retrievedData){
            getAndParseData();
        }

        return totalPopulation;
    }
    /**
     * Get the population of females in the target countries
     */
    public Map<Country, Integer> getFemalePopulation() throws IOException {
        if(!retrievedData){
            getAndParseData();
        }

        return femalePopulation;
    }
    /**
     * Get the population of males in the target countries
     */
    public Map<Country, Integer> getMalePopulation() throws IOException {
        if(!retrievedData){
            getAndParseData();
        }

        return malePopulation;
    }


    /**
     * Get the API info and parse it
     * @throws IOException If there is an issue parsing the API info
     */
    private void getAndParseData() throws IOException {
        if(retrievedData){
            return;
        }

        JSONObject data = this.getJSONData().getJSONObject("data");

        for(String countryLongName : data.keySet()){
            JSONObject countryInfo = data.getJSONObject(countryLongName);
            Country countryKey = CountryListBuilder.getInstance().getCountryByName(countryLongName);

            // value is reported in the 1000s as a real number
            String countryMalePopulationString = countryInfo.getString("malepop2020");
            Integer countryMalePopulation = null;
            if(countryMalePopulationString != null && !countryMalePopulationString.equals("")){
                countryMalePopulation= (Integer)(int)(Double.parseDouble(countryMalePopulationString)*1000);
            }else{
                countryMalePopulation = -1;
            }

            String countryFemalePopulationString = countryInfo.getString("femalepop2020");
            Integer countryFemalePopulation = null;
            if(countryFemalePopulationString != null && !countryMalePopulationString.equals("")){
                countryFemalePopulation = (Integer)(int)(Double.parseDouble(countryFemalePopulationString)*1000);
            }else{
                countryFemalePopulation = -1;
            }

            String countryCombinedPopulationString = countryInfo.getString("totpop2020");
            Integer countryCombinedPopulation = null;
            if(countryCombinedPopulationString != null && !countryCombinedPopulationString.equals("")){
                countryCombinedPopulation = (Integer)(int)(Double.parseDouble(countryCombinedPopulationString)*1000);
            }else{
                countryCombinedPopulation = -1;
            }

            malePopulation.put(countryKey, countryMalePopulation);
            femalePopulation.put(countryKey, countryFemalePopulation);
            totalPopulation.put(countryKey, countryCombinedPopulation);
        }
    }

    @Override
    public Request getDataURL() {
        return GlobalHealth5050URL.summaryURL(targets);
    }

}
