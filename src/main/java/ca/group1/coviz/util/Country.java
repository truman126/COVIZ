package ca.group1.coviz.util;

/**
 * Data class
 */
public class Country {
    private String longName;
    private String shortName;
    private Double latitude;
    private Double longitude;

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Country(String longName, String shortName, Double latitude, Double longitude) {
        this.longName = longName;
        this.shortName = shortName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Countries are equal if their names and locations  are equal
     * @param obj The other country
     * @return true if both countries are equal
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Country){
            Country other = (Country)obj;
            boolean longNamesEqual = this.longName.equals(other.longName);
            boolean shortNamesEqual = this.shortName.equals(other.shortName);
            boolean latitudeEqual = this.latitude.equals(other.latitude);
            boolean longitudeEqual = this.longitude.equals(other.longitude);

            return longNamesEqual && shortNamesEqual && latitudeEqual && longitudeEqual;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Country [longName=" + longName + ", shortName=" + shortName
                + ", latitude=" + latitude + ", longitude=" + longitude +"]";
    }

}
