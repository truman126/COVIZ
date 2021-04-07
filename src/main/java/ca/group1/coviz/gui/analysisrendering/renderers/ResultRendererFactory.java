package ca.group1.coviz.gui.analysisrendering.renderers;

import ca.group1.coviz.analysisengine.analyses.AnalysisType;
import ca.group1.coviz.analysisengine.apiutils.InvalidAnalysisSelectionException;

import java.awt.*;

/**
 * Gets the appropriate renderer for the desired analysis type
 */
public class ResultRendererFactory {
    /**
     * Get the desired renderer
     * @param t The analysis type to render
     * @return The result renderer that is appropriate for the Analysis type
     * @throws InvalidAnalysisSelectionException If an unsupported analysis was selected
     */
    public static IResultRenderer getRenderer(AnalysisType t) throws InvalidAnalysisSelectionException {
        switch (t){

            case TOTAL_CASES:
                return new ProportionalResultRenderer(1.0/10000.0, Color.RED);
            case CASES_PER_CAPITA:
                return new ProportionalResultRenderer(50000.0, Color.RED);
            case DEATHS_PER_CAPITA:
                return new ProportionalResultRenderer(500000.0, Color.BLACK);
            case MAJORITY_CASE_SEX_PER_CAPITA:
                return new ColouredResultRenderer(Color.PINK, Color.WHITE, new Color(137, 207, 240));
            default:
                throw new InvalidAnalysisSelectionException("Unsupported analysis type "+t.name());
        }
    }
}
