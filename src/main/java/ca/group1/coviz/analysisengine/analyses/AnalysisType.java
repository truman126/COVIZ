package ca.group1.coviz.analysisengine.analyses;

import ca.group1.coviz.analysisengine.util.AnalysisResult;

/**
 * AnalysisType, Used for selecting what type of Analysis to run.
 */
public enum AnalysisType {

    /**
     * An analysis that returns the total number of COVID-19 cases in the country.
     */
    TOTAL_CASES,
    /**
     * An analysis that returns the number of cases per person in the country related to COVID-19.
     */
    CASES_PER_CAPITA,
    /**
     * An analysis that returns the number of deaths per person in the country related to COVID-19.
     */
    DEATHS_PER_CAPITA,
    /**
     * An analysis that returns a scale value for whether male or female cases were the majority of cases.
     */
    MAJORITY_CASE_SEX_PER_CAPITA;

    /**
     * Gets an analysis type based on a string describing it.
     * Refer to the select analysis drop down in CovizGUI
     * @param s The string to use to select the analysis
     * @return An enum representing the desired analysis string
     */
    public static AnalysisType fromString(String s){
        switch (s.toLowerCase()){
            case "cases per capita":
                return CASES_PER_CAPITA;
            case "deaths per capita":
                return DEATHS_PER_CAPITA;
            case "majority of cases per sex":
                return MAJORITY_CASE_SEX_PER_CAPITA;
            case "total cases":
                return TOTAL_CASES;
            default:
                return null;
        }
    }
}
