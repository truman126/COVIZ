package ca.group1.coviz.queryconstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ca.group1.coviz.util.Country;

public class CountryListBuilderTest {
    @Test
    void testReadCountryDataFromFile(){
        CountryListBuilder cBuilder = CountryListBuilder.getInstance();
        assertNotNull(cBuilder);
        assertTrue(cBuilder.getAllCountries().size() > 0);
    }

    @Test
    void testCountryListSingleton(){
        CountryListBuilder instance1 = CountryListBuilder.getInstance();
        CountryListBuilder instance2 = CountryListBuilder.getInstance();

        assertEquals(instance1, instance2);
    }

    @Test
    void testCountryInfoPresent(){
        CountryListBuilder cList = CountryListBuilder.getInstance();
        Country canada = cList.getCountryByName("canada");

        assertEquals("canada", canada.getLongName());
        assertEquals("CA", canada.getShortName());
        assertEquals(60, canada.getLatitude());
        assertEquals(-95, canada.getLongitude());

        Country unitedStates = cList.getCountryByName("usa");

        assertEquals("usa", unitedStates.getLongName());
        assertEquals("US", unitedStates.getShortName());
        assertEquals(38, unitedStates.getLatitude());
        assertEquals(-97, unitedStates.getLongitude());
    }

    @Test

    void testCountryGetCountryInfo(){
        
        Country canada = CountryListBuilder.getInstance().getCountryByName("canada");
        Country usa = CountryListBuilder.getInstance().getCountryByName("usa");
        Country greece = CountryListBuilder.getInstance().getCountryByName("greece");
        
        assertEquals("canada", canada.getLongName());
        assertEquals("CA", canada.getShortName());
        assertEquals(60, canada.getLatitude());
        assertEquals(-95, canada.getLongitude());

        assertEquals("usa", usa.getLongName());
        assertEquals("US", usa.getShortName());
        assertEquals(38, usa.getLatitude());
        assertEquals(-97, usa.getLongitude());

        assertEquals("greece", greece.getLongName());
        assertEquals("GR", greece.getShortName());
        assertEquals(39, greece.getLatitude());
        assertEquals(22, greece.getLongitude());
    }

    @Test
    void testGetCountryInfoByName(){
        Country canada = CountryListBuilder.getInstance().getCountryByName("canada");
        Country canada1 = CountryListBuilder.getInstance().getCountryByName("Canada");
        Country canada2 = CountryListBuilder.getInstance().getCountryByName("CANADA");

        assertSame(canada, canada1);
        assertSame(canada, canada2);
        assertSame(canada1, canada2);
    }
}
