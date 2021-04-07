package ca.group1.coviz.analysisengine.apiutils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ca.group1.coviz.analysisengine.util.AnalysisResult;

/**
 * Analysis Interface, all analyses that can be run on countries will implement this
 */
public interface IAnalysis {
    /**
     * Executes an analysis
     * @return The result of the analysis
     * @throws InterruptedException varies by implementor
     * @throws ExecutionException varies by implementor
     * @throws IOException varies by implementor
     */
    AnalysisResult analyze() throws InterruptedException, ExecutionException, IOException;
}
