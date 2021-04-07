package ca.group1.coviz.analysisengine;

import ca.group1.coviz.analysisengine.analyses.AnalysisType;
import ca.group1.coviz.analysisengine.apiutils.InvalidAnalysisSelectionException;
import ca.group1.coviz.analysisengine.util.AnalysisFactory;
import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.analysisengine.apiutils.IAnalysis;
import ca.group1.coviz.queryconstruction.CountryListBuilder;
import ca.group1.coviz.util.Country;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Executes analyses based on a list of targets and a selected analysis type
 */
public class AnalysisExecutor {
    private AnalysisType analysisType = null;
    private AnalysisResult results = null;

    /**
     * Gets the current analysis type that the Analysis Executor will execute
     * @return The analysis type that the analysis executor is preparing to execute
     */
    public AnalysisType getAnalysisType() {
        return analysisType;
    }

    /**
     * Set the analysis type for the analysis executor to execute
     * @param analysisType The desired analysis type
     */
    public void setAnalysisType(AnalysisType analysisType) {
        this.analysisType = analysisType;
    }

    /**
     * Executes the selected analysis on the target countries
     * @param targets The list of targets to run the analysis on
     * @return The result of the analysis
     * @throws InterruptedException If the futures in charge of getting API info were interrupted
     * @throws ExecutionException If the futures in charge of getting the API info ran into an issue
     * @throws IOException If there was an issue parsing the JSON
     * @throws InvalidAnalysisSelectionException If an invalid analysis type was requested
     */
    public AnalysisResult runAnalysis(List<Country> targets) throws InterruptedException, ExecutionException, IOException, InvalidAnalysisSelectionException {
        IAnalysis analysis = AnalysisFactory.prepareAnalysis(this.getAnalysisType(), targets);
        this.results = analysis.analyze();
        return this.results;
    }

    /**
     * Check if the executor is prepared to run runAnalysis
     * @return true if the runAnalysis can be called with all prerequisites
     */
    public boolean canAnalyze(){
        return analysisType != null;
    }

    /**
     * gets the last analysis executed by this analysisExecutor
     * @return The analysis result
     */
    public AnalysisResult getResults() {
        return results;
    }

    /**
     * Whether the analysis executor holds a previous analysis
     * @return true if there is an analysis contained in this analysis executor
     */
    public boolean hasResult(){
        return this.results != null;
    }
}
