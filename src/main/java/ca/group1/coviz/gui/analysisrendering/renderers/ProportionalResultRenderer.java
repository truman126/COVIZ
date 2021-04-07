package ca.group1.coviz.gui.analysisrendering.renderers;

import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.gui.analysisrendering.util.CountryResultCircle;
import ca.group1.coviz.gui.analysisrendering.util.RenderResult;
import ca.group1.coviz.util.Country;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders a larger circle for a larger measure
 */
class ProportionalResultRenderer implements IResultRenderer{
    Double scale;
    Color color;

    /**
     * Construct the proportional result renderer
     * @param scale A scale to multiply all the measures by
     * @param color The colour to draw the circles with (All proportional circles are one colour)
     */
    public ProportionalResultRenderer(Double scale, Color color) {
        this.scale = scale;
        this.color = color;
    }

    /**
     * Render the analysisResult
     * @param inputInfo The analysisResult to render
     * @return The rendered result of the analysis
     */
    @Override
    public RenderResult render(AnalysisResult inputInfo) {
        Map<Country, CountryResultCircle> toRender = new HashMap<>();
        for(Country c : inputInfo.getRenderableData().keySet()){
            Double countryMeasure = inputInfo.getRenderableData().get(c);
            toRender.put(c, new CountryResultCircle(getCircleSize(countryMeasure), c.getLatitude(), c.getLongitude(), color));
        }
        return new RenderResult(inputInfo.getRawData(), toRender);
    }

    /**
     * Gets the size of a circle
     * @param measure The measure to use
     * @return The radius of the circle
     */
    private Double getCircleSize(Double measure){
        return Math.sqrt((measure * scale)/Math.PI);
    }
}
