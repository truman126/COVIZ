package ca.group1.coviz.analysisengine.apiutils;

/**
 * An exception that is raised if an invalid AnalysisType was selected
 */
public class InvalidAnalysisSelectionException extends Exception{
    public InvalidAnalysisSelectionException(String s) {
        super(s);
    }
}
