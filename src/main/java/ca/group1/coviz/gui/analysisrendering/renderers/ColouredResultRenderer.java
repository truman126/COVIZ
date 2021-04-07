package ca.group1.coviz.gui.analysisrendering.renderers;

import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.gui.analysisrendering.util.CountryResultCircle;
import ca.group1.coviz.gui.analysisrendering.util.RenderResult;
import ca.group1.coviz.util.Country;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A Result Renderer that takes measures in the range of [0, 1] and gives a circle witha  colour
 */
class ColouredResultRenderer implements IResultRenderer{
    Color low; // color for lower values
    Color mid; // color for middle values. Can be null if only the endpoints need to be used for the colour scale
    Color high; // color for higher values

    /**
     * Construct a result renderer that only has a low and high color range
     * @param low the color to use if a given value is low
     * @param mid the color to use between the low and high values
     * @param high the color to use if a given value is high
     */
    public ColouredResultRenderer(Color low, Color mid, Color high) {
        this.low = low;
        this.mid = mid;
        this.high = high;
    }

    /**
     * Construct a result renderer that only has a low and high color range
     * @param low the color to use if a given value is low
     * @param high the color to use if a given value is high
     */
    public ColouredResultRenderer(Color low, Color high) {
        this.low = low;
        this.high = high;
    }

    /**
     * Render the analysisResult
     * @param inputInfo The Analysis to render
     * @return A Render Result with circle colours and sizes
     * @throws AssertionError If the metric for a given country is not in the range [0, 1]
     */
    @Override
    public RenderResult render(AnalysisResult inputInfo) throws AssertionError{
        Map<Country, CountryResultCircle> circlesOut = new HashMap<>();
        for(Country c : inputInfo.getRenderableData().keySet()){
            Double countryMetric = inputInfo.getRenderableData().get(c);

            assert 0<= countryMetric && countryMetric <= 1;

            circlesOut.put(c, new CountryResultCircle(10.0, c.getLatitude(), c.getLongitude(), getCircleColor(countryMetric)));
        }
        return new RenderResult(inputInfo.getRawData(), circlesOut);
    }

    /**
     * Calculates the colour of a circle for a given metric
     * @param measure The metric to choose the circle colour with
     * @return The Circle colour for the metric
     */
    private Color getCircleColor(Double measure){
        if(mid == null){ // two colours
            return getTwoColourMeasure(low, high, measure.floatValue());
        }
        else{ // three colours
            if(measure > 0.5){ // mid-high colour blend
                measure = 2 * (measure - 0.5);

                return getTwoColourMeasure(mid, high, measure.floatValue());
            }else{ // mid-low colour blend
                measure = 2 * measure;

                return getTwoColourMeasure(low, mid, measure.floatValue());
            }
        }
    }

    /**
     * Gets the result of a 2 colour blend with a measure for scale between
     * @param low The low colour in the range
     * @param high The high colour in the range
     * @param measure The selector for how much of each colour to blend
     * @return The resultant colour
     */
    private Color getTwoColourMeasure(Color low, Color high, float measure){
        float rOut = Math.min(255, Math.max(0, low.getRed() * measure + high.getRed() * (1-measure)))/255.f;
        float gOut = Math.min(255, Math.max(0, low.getGreen() * measure + high.getGreen() * (1-measure)))/255.f;
        float bOut = Math.min(255, Math.max(0, low.getBlue() * measure + high.getBlue() * (1-measure)))/255.f;

        return new Color(rOut, gOut, bOut);
    }
}
