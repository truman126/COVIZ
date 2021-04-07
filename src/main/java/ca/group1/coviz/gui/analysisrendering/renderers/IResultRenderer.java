package ca.group1.coviz.gui.analysisrendering.renderers;

import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.gui.analysisrendering.util.RenderResult;
import ca.group1.coviz.util.Country;

import java.util.Map;

/**
 * An interface for result renderers
 */
public interface IResultRenderer {
    /**
     * Renders an analysis result
     * @param inputInfo The analysis result to render
     * @return The render result
     */
    RenderResult render(AnalysisResult inputInfo);
}
