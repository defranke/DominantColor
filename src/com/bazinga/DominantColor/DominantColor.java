package com.bazinga.DominantColor;

import com.bazinga.DominantColor.WeightFunctions.WeightFunction;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennis on 14.05.15.
 */
public class DominantColor {

    private BufferedImage image = null;
    private int maxImageSize;


    public DominantColor() {
        this(512);
    }

    public DominantColor(int maxImageSize) {
        this.maxImageSize = maxImageSize;
    }


    public Color analyzeImage(WeightFunction weight) {
        image = DominantColor.getScaledImage(image, maxImageSize);


        // 1. get dominant color for shift = 6
        // => 0 => 0, 64 => 1, 128 => 2
        ShiftedColor dominantColorShifted = getDominantColor(null, 6, weight);
        dominantColorShifted = getDominantColor(dominantColorShifted, 4, weight);
        dominantColorShifted = getDominantColor(dominantColorShifted, 2, weight);
        dominantColorShifted = getDominantColor(dominantColorShifted, 0, weight);

        if(dominantColorShifted == null)
            return null;

        return dominantColorShifted.toColor();
    }


    private ShiftedColor getDominantColor(ShiftedColor prevDominantColor, int shifting, WeightFunction weight) {
        if(image == null) return null;

        // shifted r,g,b => count
        HashMap<ShiftedColor, Integer> histogram = new HashMap<>();

        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y));
                // Does the color match the previous dominant color with a larger shift?
                if(!useColor(prevDominantColor, c)) continue;

                ShiftedColor shiftedColor = new ShiftedColor(c, shifting);

                int val = 0;
                if(histogram.containsKey(shiftedColor)) {
                    val = histogram.get(shiftedColor);
                }
                if(weight != null) {
                    val += weight.getWeight(c);
                }else{
                    val++;
                }
                histogram.put(shiftedColor, val);
            }
        }


        // Get Maximum
        /*Map.Entry<ShiftedColor, Integer> max = histogram.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Integer::compareTo))
                .get();*/


        Map.Entry<ShiftedColor, Integer> max = histogram.entrySet()
                .stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .get();

        return max.getKey();
    }


    private boolean useColor(ShiftedColor prevDominantColor, Color c) {
        if(prevDominantColor == null) return true;
        ShiftedColor currentColorShifted = new ShiftedColor(c, prevDominantColor.shifted);
        return prevDominantColor.equals(currentColorShifted);
    }










    public void setImage(javafx.scene.image.Image fximage) {
        if(fximage == null) return;
        image = SwingFXUtils.fromFXImage(fximage, null);
    }

    public void setImage(BufferedImage img) {
        if(img == null) return;
        this.image = img;
    }

    public void setImage(Image img) {
        if(img == null) return;
        this.image = DominantColor.toBufferedImage(img);
    }








    /*
     * HELPER FUNCTIONS
     */

    public static BufferedImage getScaledImage(BufferedImage image, int maxImageSize) {
        if(image == null) return image;
        if(image.getWidth() < maxImageSize && image.getHeight() < maxImageSize) return image;

        // Needs rescaling
        int newHeight = 0;
        int newWidth = 0;
        if(image.getWidth() >= image.getHeight()) {
            newWidth = maxImageSize;
            newHeight = (int)(image.getHeight() * (maxImageSize / (float)image.getWidth()));
        }else{
            newHeight = maxImageSize;
            newWidth = (int)(image.getWidth() * (maxImageSize / (float)image.getHeight()));
        }
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        image = DominantColor.toBufferedImage(scaledImage);
        return DominantColor.toBufferedImage(image);
    }




    public static BufferedImage toBufferedImage(Image img)  {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
