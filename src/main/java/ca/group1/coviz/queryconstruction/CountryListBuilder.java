package ca.group1.coviz.queryconstruction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.group1.coviz.util.Country;

/**
 * The CountryListBuilder, a master list of countries
 */
public class CountryListBuilder {
    private static final HashMap<String, Country> masterCountryList = new HashMap<>();
    private static boolean initializedCorrectly = false;
    static {
        /*
         * Initialize the master country list
         */
        Path countriesFile = Paths.get("data/countries.json");
        List<String> fileLines;
        try {
            fileLines = Files.readAllLines(countriesFile);

            // the JSON library has a finnicky implementation of iterator
            // it wouldn't let be do a for-each loop so I'm using an iterator
            Iterator<Object> data = new JSONArray(String.join("\n", fileLines)).iterator();

            while (data.hasNext()) {
                JSONObject countryData = (JSONObject) data.next();

                String longName = countryData.getString("name").toLowerCase();
                String shortName = countryData.getString("country_code");
                JSONArray latlong = countryData.getJSONArray("latlng");
                Double lat = latlong.getDouble(0);
                Double lng = latlong.getDouble(1);

                Country countryOut = new Country(longName, shortName, lat, lng);
                masterCountryList.put(longName, countryOut);
            }
            initializedCorrectly = true;
        } catch (IOException e) {
            // If there was an error reading the file, print the error.
            // Since a static block can't throw an exception, silently let the error pass but
            // don't set the initializedCorrectly flag
            e.printStackTrace();

        }
    }

    private static CountryListBuilder builderInstance = null;
    private final ArrayList<Country> countryList = new ArrayList<>();

    private CountryListBuilder(){}

    /**
     * gets the singleton instance of CountryListBuilder
     * @return The instance of CountryListBuilder
     */
    public static CountryListBuilder getInstance() {
        if (builderInstance == null) {
            builderInstance = new CountryListBuilder();
        }
        return builderInstance;
    }

    /**
     * Check if the CountryListBuilder encountered an error while constructing the country list
     * @return True if the CountryListBuilder is ready to function normally
     */
    public static boolean isInitializedCorrectly() {
        /*
         * Returns false if an error occured while constructing the master country list
         */
        return initializedCorrectly;
    }

    /**
     * Gets the master list of countries
     * @return A List of countries that the country list builder recognizes
     */
    public List<Country> getAllCountries() {
        return new ArrayList<>(masterCountryList.values());
    }

    /**
     * Gets the list of countries that the countryListBuilder is constructing
     * @return The list of countries that the CountryListBuilder is building
     */
    public List<Country> getList(){
        return countryList;
    }

    /**
     * Add a country to the country list
     * @param country The new country to add
     * @return true if the country was added. False if it was already present
     */
    public Boolean addCountry(Country country){
        if(countryList.contains(country)){
            return false;
        }
        countryList.add(country);
        return true;
    }

    /**
     * Add a country by name to the country list
     * @param countryName The name of the country to add
     * @return True if the country was successfully added, false if the country was already present, or the name was not recognized
     */
    public Boolean addCountry(String countryName){
        Country toAdd = this.getCountryByName(countryName);
        if(toAdd == null){
            return false;
        }

        return this.addCountry(toAdd);
    }

    /**
     * Removes a counntry from the country list
     * @param country The country to remove
     * @return true if the country was removed properly
     */
    public Boolean removeCountry(Country country){
        return countryList.remove(country);
    }

    /**
     * Get a country by its long name
     * @return the Country from the master country list
     */
    public Country getCountryByName(String longName){
        return masterCountryList.get(longName.toLowerCase());
    }

    /**
     * Clears the list that this country list builder is building
     */
    public void clearList(){
        this.countryList.clear();
    }
}
