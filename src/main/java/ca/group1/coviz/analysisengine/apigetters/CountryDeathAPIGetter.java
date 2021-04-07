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
 * Gets the number of deaths in the target countries
 */
public class CountryDeathAPIGetter extends JSONAPIInformationGetter {
    List<Country> targets;
    boolean retrievedData = false;
    Map<Country, Integer> maleDeaths;
    Map<Country, Integer> femaleDeaths;
    Map<Country, Integer> combinedDeaths;

    /**
     * Construct the API getter
     * @param targets The list of target countries for the death analysis
     */
    public CountryDeathAPIGetter(List<Country> targets) {
        this.targets = targets;
    }

    /**
     * Gets data from the API and parses it
     * @throws IOException If there was an issue parsing the JSON
     */
    private void getAndParseData() throws IOException {
        if(retrievedData){
            return;
        }

        JSONObject data = this.getJSONData().getJSONObject("data");
        maleDeaths = new HashMap<>();
        femaleDeaths = new HashMap<>();
        combinedDeaths = new HashMap<>();

        for(String countryLongName : data.keySet()){
            JSONObject countryInfo = data.getJSONObject(countryLongName);
            Country countryKey = CountryListBuilder.getInstance().getCountryByName(countryLongName);

            // yes, the API returns a JSON where deaths is a string
            String maleDeathString = countryInfo.getString("deaths_male");
            Integer countryMaleDeaths = null;
            if(maleDeathString != null && !maleDeathString.equals("")){
                countryMaleDeaths = Integer.parseInt(maleDeathString);
            }
            else{
                countryMaleDeaths = -1;
            }

            String femaleDeathString = countryInfo.getString("deaths_female");
            Integer countryFemaleDeaths = null;
            if(femaleDeathString != null && !femaleDeathString.equals("")){
                countryFemaleDeaths = Integer.parseInt(femaleDeathString);
            }else{
                countryFemaleDeaths = -1;
            }

            String combinedDeathString = countryInfo.getString("deaths_total");
            Integer countryCombinedDeaths =null;
            if(combinedDeathString != null && !combinedDeathString.equals("")){
                countryCombinedDeaths= Integer.parseInt(combinedDeathString);
            }else{
                countryCombinedDeaths = -1;
            }

            maleDeaths.put(countryKey, countryMaleDeaths);
            femaleDeaths.put(countryKey, countryFemaleDeaths);
            combinedDeaths.put(countryKey, countryCombinedDeaths);
        }
        retrievedData = true;
    }

    @Override
    public Request getDataURL() {
        return GlobalHealth5050URL.summaryURL(targets);
    }

    /**
     * Gets the number of male deaths due to covid in the country
     * @return The number of deaths in the country by deaths
     * @throws IOException If there is an issue parsing the JSON
     */
    public Map<Country, Integer> getMaleDeaths() throws IOException {
        if(!retrievedData){
            this.getAndParseData();
        }
        return maleDeaths;
    }

    /**
     * Get the number of female deaths due to covid in the country
     * @return The number of female deaths in the country by deaths
     * @throws IOException If there is an issue parsing the JSON
     */
    public Map<Country, Integer> getFemaleDeaths() throws IOException {
        if(!retrievedData){
            this.getAndParseData();
        }
        return femaleDeaths;
    }

    /**
     * Gets the number of total deaths due to covid in the country
     * @return The number of total deaths in the country
     * @throws IOException If there is an issue parsing the JSON
     */
    public Map<Country, Integer> getCombinedDeaths() throws IOException {
        if(!retrievedData){
            this.getAndParseData();
        }
        return combinedDeaths;
    }

}
