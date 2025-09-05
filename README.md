**ImageForgeFX**

A JavaFX-based image processing tool that applies real-time filters and effects on images using multithreaded processing.
It provides a modular architecture for experimenting with custom filters and visualizing results on an interactive canvas.

✨ Features

🎨 Apply custom filters (e.g., grayscale) to images.

⚡ Multithreaded processing for faster performance.

🖼️ Real-time visualization on a JavaFX canvas.

📂 Easy to swap images (src/main/resources/test.jpg).

🛠️ Maven-powered build for easy dependency management.

📂 Project Structure
ImageForgeFX
 ├─ src/main/java/com/image/imageprocessing
 │   ├─ HelloApplication.java   # Entry point
 
 │   ├─ processor/              # ImageProcessor (multithreaded logic)
 
 │   ├─ filter/                 # Custom filters
 
 │   ├─ image/                  # Drawing on canvas
 
 │   └─ io/                     # Image IO handling
 
 └─ src/main/resources/test.jpg # Sample image
 
