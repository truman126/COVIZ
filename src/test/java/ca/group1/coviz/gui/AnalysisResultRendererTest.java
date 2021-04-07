package ca.group1.coviz.gui;

import ca.group1.coviz.analysisengine.AnalysisExecutor;
import ca.group1.coviz.analysisengine.analyses.AnalysisType;
import ca.group1.coviz.analysisengine.apiutils.InvalidAnalysisSelectionException;
import ca.group1.coviz.gui.analysisrendering.AnalysisResultRenderer;
import ca.group1.coviz.gui.analysisrendering.util.RenderResult;
import ca.group1.coviz.queryconstruction.CountryListBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class AnalysisResultRendererTest {

    @Test
    void checkNoErrorAndHasResults(){
        AnalysisExecutor executor = new AnalysisExecutor();
        AnalysisResultRenderer renderer = new AnalysisResultRenderer(executor);

        // no analysis type selected yet
        assertFalse(executor.canAnalyze());

        // select analysis type
        executor.setAnalysisType(AnalysisType.CASES_PER_CAPITA);

        // now we have an analysis type
        assertTrue(executor.canAnalyze());

        CountryListBuilder targets = CountryListBuilder.getInstance();
        targets.clearList();
        assertTrue(targets.addCountry("Canada"));
        assertTrue(targets.addCountry("USA"));
        assertTrue(targets.addCountry("Italy"));
        assertTrue(targets.addCountry("Greece"));
        assertTrue(targets.addCountry("China"));

        // we have not actually run the analysis yet
        assertFalse(renderer.canRender());

        try {
            executor.runAnalysis(targets.getList());
        } catch (InterruptedException e) {
            // if we had a UI, these need to be handled to produce meaningful error messages
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAnalysisSelectionException e) {
            e.printStackTrace();
        }

        // we've now run an analysis and we can render it
        assertTrue(renderer.canRender());

        RenderResult result = null;
        try {
            // get the render result
            result = renderer.render();
        } catch (InvalidAnalysisSelectionException e) {
            e.printStackTrace();
        }

        // assert we didn't encounter an error
        assertNotNull(result);

        // print the result to sanity check
        System.out.println(result);
    }
}
