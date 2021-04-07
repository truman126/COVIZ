package ca.group1.coviz.gui.analysisrendering.util;

import ca.group1.coviz.util.Country;

import java.util.Map;

public class RenderResult {
    Map<Country, Map<String, Double>> rawData;
    Map<Country, CountryResultCircle> circles;

    public Map<Country, Map<String, Double>> getRawData() {
        return rawData;
    }

    public void setRawData(Map<Country, Map<String, Double>> rawData) {
        this.rawData = rawData;
    }

    public Map<Country, CountryResultCircle> getCircles() {
        return circles;
    }

    public void setCircles(Map<Country, CountryResultCircle> circles) {
        this.circles = circles;
    }

    public RenderResult(Map<Country, Map<String, Double>> rawData, Map<Country, CountryResultCircle> circles) {
        this.rawData = rawData;
        this.circles = circles;
    }

    @Override
    public String toString() {
        return rawData.toString()+"\n"+circles.toString();
    }
}
