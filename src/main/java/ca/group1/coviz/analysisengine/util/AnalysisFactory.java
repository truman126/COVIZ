package ca.group1.coviz.analysisengine.util;

import ca.group1.coviz.analysisengine.analyses.*;
import ca.group1.coviz.analysisengine.apiutils.IAnalysis;
import ca.group1.coviz.analysisengine.apiutils.InvalidAnalysisSelectionException;
import ca.group1.coviz.util.Country;

import java.util.List;

/**
 * A factory that returns the proper analysis based on the analysis type and targets
 */
public class AnalysisFactory {
    /**
     * Prepares an analysis of the desired type to be executed
     * @param type The type of analysis to execute
     * @param targets The target countries of the analysis
     * @return An analysis that can be executed
     * @throws InvalidAnalysisSelectionException If the desired analysis type is not supported by this factory
     */
    public static IAnalysis prepareAnalysis(AnalysisType type, List<Country> targets) throws InvalidAnalysisSelectionException {
        switch(type){
            case TOTAL_CASES:
                return new TotalCaseAnalysis(targets);
            case CASES_PER_CAPITA:
                return new CasesPerCapitaAnalysis(targets);
            case DEATHS_PER_CAPITA:
                return new DeathsPerCapitaAnalysis(targets);
            case MAJORITY_CASE_SEX_PER_CAPITA:
                return new MajorityCaseSexPerCapitaAnalysis(targets);
            default:
                throw new InvalidAnalysisSelectionException("This Factory has not been updated to support "+type.name());
        }
    }
}
