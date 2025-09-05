package com.image.imageprocessing;

import com.image.imageprocessing.filter.GreyScaleFilter;
import com.image.imageprocessing.filter.ImageFilter;
import com.image.imageprocessing.image.DrawMultipleImagesOnCanvas;
import com.image.imageprocessing.processor.ImageProcessor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Initialize canvas
            DrawMultipleImagesOnCanvas drawMultipleImagesOnCanvas = DrawMultipleImagesOnCanvas.getInstance();
            drawMultipleImagesOnCanvas.initialize(stage);

            // Load image from resources (src/main/resources/test.jpg)
            ImageProcessor processor = new ImageProcessor();
            BufferedImage image = processor.loadImage("/test.jpg");

            // Apply filter
            ImageFilter imageFilter = new GreyScaleFilter();

            // Process & draw
            processor.processImage(image, 10, imageFilter, drawMultipleImagesOnCanvas);

            Platform.setImplicitExit(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
