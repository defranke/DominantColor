package com.bazinga;

import com.bazinga.DominantColor.DominantColor;
import com.bazinga.DominantColor.WeightFunctions.DefaultWeight;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Image img = null;
        try {
            img = ImageIO.read(new File("example.jpg"));
        }catch (IOException e) {
            return;
        }

	    // Max Image size of 128x128. Larger images are scaled down
        DominantColor dominantColorAnalyzer = new DominantColor(128);
        // Set the image
        dominantColorAnalyzer.setImage(img);
        // Get the dominant color based on the weights
        Color dominantColor = dominantColorAnalyzer.analyzeImage(DefaultWeight::favorBrightExcludeWhite);

        System.out.println(dominantColor);
    }
}
