package ca.group1.coviz.analysisengine.apigetters;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ca.group1.coviz.queryconstruction.CountryListBuilder;
import ca.group1.coviz.util.Country;

public class CountryCaseAPIGetterTest {
    @Test
    public void getsFromAPIwithNoError() throws IOException {
        CountryListBuilder countryList = CountryListBuilder.getInstance();
        countryList.clearList();

        countryList.addCountry(countryList.getCountryByName("canada"));
        countryList.addCountry(countryList.getCountryByName("greece"));
        countryList.addCountry(countryList.getCountryByName("usa"));

        List<Country> targets = countryList.getList();

        CountryCaseAPIGetter countryCases = new CountryCaseAPIGetter(targets);

        Map<Country, Integer> cases = countryCases.getMaleCases();
        Map<Country, Integer> minimumValues = new HashMap<>();

        minimumValues.put(countryList.getCountryByName("usa"), 3208204);
        minimumValues.put(countryList.getCountryByName("canada"), 12115);
        minimumValues.put(countryList.getCountryByName("greece"), 29880);

        for(Country c: cases.keySet()){
            assertTrue(cases.get(c) >= minimumValues.get(c));
        }
    }
}
