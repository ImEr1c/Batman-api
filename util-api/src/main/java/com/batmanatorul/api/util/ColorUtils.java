package com.batmanatorul.api.util;

import net.minecraft.util.math.ColorHelper;

public class ColorUtils {
    public static float getRgbFloatColor(int color) {
        return 1f / color;
    }

    public static RgbFloatColor getRgbFloatColor(int a, int r, int g, int b) {
        return new RgbFloatColor(getRgbFloatColor(a), getRgbFloatColor(r), getRgbFloatColor(g), getRgbFloatColor(b));
    }

    public static int getTextColor(int a, int r, int g, int b) {
        return ColorHelper.Argb.getArgb(a, r, g, b);
    }
}
