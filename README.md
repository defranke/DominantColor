# DominantColor
Java Implementation of [color-finder](https://github.com/pieroxy/color-finder)

# Example

````
// Max Image size of 128x128. Larger images are scaled down
DominantColor dominantColorAnalyzer = new DominantColor(128);
// Set the image
dominantColorAnalyzer.setImage(img);
// Get the dominant color based on the weights
Color dominantColor = dominantColorAnalyzer.analyzeImage(DefaultWeight::favorBrightExcludeWhite);
```