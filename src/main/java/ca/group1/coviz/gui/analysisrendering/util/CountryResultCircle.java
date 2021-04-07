package ca.group1.coviz.gui.analysisrendering.util;

import java.awt.*;

public class CountryResultCircle {
    Double radius;
    Double lat;
    Double lng;
    Color color;

    @Override
    public String toString() {
        return "CountryResultCircle{" +
            "radius=" + radius +
            ", lat=" + lat +
            ", lng=" + lng +
            ", color=" + color +
            '}';
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public CountryResultCircle(Double radius, Double lat, Double lng, Color color) {
        this.radius = radius;
        this.lat = lat;
        this.lng = lng;
        this.color = color;
    }
}
