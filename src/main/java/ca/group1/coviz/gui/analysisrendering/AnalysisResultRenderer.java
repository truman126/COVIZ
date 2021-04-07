package ca.group1.coviz.gui.analysisrendering;

import ca.group1.coviz.analysisengine.AnalysisExecutor;
import ca.group1.coviz.analysisengine.apiutils.InvalidAnalysisSelectionException;
import ca.group1.coviz.gui.analysisrendering.renderers.IResultRenderer;
import ca.group1.coviz.gui.analysisrendering.renderers.ResultRendererFactory;
import ca.group1.coviz.gui.analysisrendering.util.RenderResult;

/**
 * Renders the result of an analysis
 */
public class AnalysisResultRenderer {
    AnalysisExecutor executor;

    /**
     * Constructs the AnalysisResultRenderer
     * @param executor The AnalysisExecutor that the result information will be pulled from
     */
    public AnalysisResultRenderer(AnalysisExecutor executor) {
        this.executor = executor;
    }

    /**
     * attempts to render the Analysis result
     * @return The render result, null if rendering failed
     * @throws InvalidAnalysisSelectionException If an invalid analysis type was selected
     */
    public RenderResult render() throws InvalidAnalysisSelectionException {
        IResultRenderer renderer = ResultRendererFactory.getRenderer(executor.getAnalysisType());

        if(executor.hasResult()) {
            return renderer.render(executor.getResults());
        }else{
            return null;
        }
    }

    public boolean canRender(){
        return executor.hasResult();
    }
}
