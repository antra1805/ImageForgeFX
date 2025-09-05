package com.image.imageprocessing.processor;

import com.image.imageprocessing.filter.ImageFilter;
import com.image.imageprocessing.image.DrawMultipleImagesOnCanvas;
import com.image.imageprocessing.image.ImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ImageProcessor {

    private final ExecutorService executorService;

    public ImageProcessor() {
        executorService = Executors.newFixedThreadPool(100);
    }

    /**
     * Load an image from resources folder.
     * Example: "/test.jpg" (if placed under src/main/resources/test.jpg)
     */
    public BufferedImage loadImage(String resourcePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            BufferedImage img = ImageIO.read(is);
            if (img == null) {
                throw new IOException("ImageIO could not read image: " + resourcePath);
            }
            return img;
        }
    }

    /**
     * Process the given image with a filter and draw function.
     */
    public void processImage(BufferedImage image, int num,
                             ImageFilter imageFilter, DrawMultipleImagesOnCanvas drawFn) {

        if (image == null) {
            throw new IllegalArgumentException("Input image is null. Did you call loadImage()?");
        }

        int numHorizontalImages = image.getWidth() / num;
        int numVerticalImages = image.getHeight() / num;

        List<Future<ImageData>> futures = new ArrayList<>();

        for (int i = 0; i < numHorizontalImages; i++) {
            for (int j = 0; j < numVerticalImages; j++) {
                BufferedImage subImage = image.getSubimage(i * num, j * num, num, num);
                int finalI = i;
                int finalJ = j;

                Future<ImageData> future = executorService.submit(() -> {
                    BufferedImage result = imageFilter.filter(subImage);
                    ImageData imageData = new ImageData(result, finalI * num, finalJ * num, num, num);
                    // Add to queue immediately when processing is complete
                    drawFn.addImageToQueue(imageData);
                    return imageData;
                });
                futures.add(future);
            }
        }

        // Wait for all processing tasks to finish
        for (Future<ImageData> future : futures) {
            try {
                future.get();
            } catch (Exception ex) {
                System.err.println("Error processing image: " + ex.getMessage());
            }
        }
    }
}
