package ca.group1.coviz.analysisengine.util;

import ca.group1.coviz.analysisengine.analyses.AnalysisType;
import ca.group1.coviz.util.Country;

import java.util.Map;

public class AnalysisResult {
    private Map<Country, Map<String, Double>> rawData;
    private Map<Country, Double> renderableData;
    private AnalysisType analysisType;

    public AnalysisResult(AnalysisType analysisType, Map<Country, Map<String, Double>> rawData, Map<Country, Double> renderableData) {
        this.rawData = rawData;
        this.renderableData = renderableData;
        this.analysisType = analysisType;
    }

    public Map<Country, Map<String, Double>> getRawData() {
        return rawData;
    }

    public void setRawData(Map<Country, Map<String, Double>> rawData) {
        this.rawData = rawData;
    }

    public Map<Country, Double> getRenderableData() {
        return renderableData;
    }

    public void setRenderableData(Map<Country, Double> renderableData) {
        this.renderableData = renderableData;
    }
}
