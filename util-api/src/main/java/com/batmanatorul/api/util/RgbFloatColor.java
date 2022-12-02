package com.batmanatorul.api.util;

public class RgbFloatColor {
    private final float alpha;
    private final float red;
    private final float green;
    private final float blue;

    public RgbFloatColor(float alpha, float red, float green, float blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }
}
