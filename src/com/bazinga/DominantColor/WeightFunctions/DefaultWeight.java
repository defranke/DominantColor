package com.bazinga.DominantColor.WeightFunctions;

import java.awt.*;

/**
 * Created by dennis on 06.06.15.
 */
public class DefaultWeight {

    public static int favorHue(Color c) {
        return (Math.abs(c.getRed()-c.getGreen())*Math.abs(c.getRed()-c.getGreen()) +
                Math.abs(c.getRed()-c.getBlue())*Math.abs(c.getRed()-c.getBlue()) +
                Math.abs(c.getGreen()-c.getBlue())*Math.abs(c.getGreen()-c.getBlue()))/65535*50+1;
    }

    public static int favorBright(Color c) {
        return c.getRed()+c.getGreen()+c.getBlue()+1;
    }

    public static int favorDark(Color c) {
        return 768-c.getRed()-c.getBlue() -c.getGreen()+1;
    }

    public static int excludeWhite(Color c) {
        if(c.getRed() > 245 && c.getGreen() > 245 && c.getBlue() > 245) {
            return 0;
        }
        return 1;
    }

    public static int excludeBlack(Color c) {
        if(c.getRed() < 10 && c.getGreen() < 10 && c.getBlue() < 10) {
            return 0;
        }
        return 1;
    }

    public static int favorBrightExcludeWhite(Color c) {
        if(c.getRed() > 245 && c.getGreen() > 245 && c.getBlue() > 245) return 0;
        return (c.getRed()*c.getRed()+c.getGreen()*c.getGreen()+c.getBlue()*c.getBlue())/65535*20+1;
    }
}
