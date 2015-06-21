package com.bazinga.DominantColor;

import java.awt.*;

/**
 * Created by dennis on 06.06.15.
 */
public class ShiftedColor {
    public int shiftedRed;
    public int shiftedGreen;
    public int shiftedBlue;
    public int shifted;

    public ShiftedColor(int r, int g, int b, int shifted) {
        this.shifted = shifted;
        this.shiftedRed = r >> shifted;
        this.shiftedGreen = g >> shifted;
        this.shiftedBlue = b >> shifted;
    }

    public ShiftedColor(Color c, int shifted) {
        this.shifted = shifted;
        this.shiftedRed = c.getRed() >> shifted;
        this.shiftedGreen = c.getGreen() >> shifted;
        this.shiftedBlue = c.getBlue() >> shifted;
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof ShiftedColor)) return false;
        ShiftedColor c = (ShiftedColor) obj;
        if(this.shiftedGreen == c.shiftedGreen &&
                this.shiftedBlue == c.shiftedBlue &&
                this.shiftedRed == c.shiftedRed) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (shiftedRed << 16) | (shiftedGreen << 8) | (shiftedBlue);
    }

    public Color toColor() {
        return new Color(shiftedRed << shifted, shiftedGreen << shifted, shiftedBlue << shifted);
    }
}
