package ca.group1.coviz.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CountryTest {
    @Test
    void testEqual(){
        Country country1 = new Country("longName", "shortName", 0.0, 0.0);
        Country country2 = new Country("longName", "shortName", 0.0, 0.0); // same
        Country country3 = new Country("longNamex", "shortName", 0.0, 0.0); // long name different
        Country country4 = new Country("longName", "shortNamex", 0.0, 0.0); // short name different
        Country country5 = new Country("longName", "shortName", 1.0, 0.0); // lat different
        Country country6 = new Country("longName", "shortName", 0.0, 2.0); // long different
        assertEquals(country1, country2);
        assertNotEquals(country1, country3);
        assertNotEquals(country1, country4);
        assertNotEquals(country1, country5);
        assertNotEquals(country1, country6);

    }
}
