# Random Image Generator

A modern JavaFX application for discovering random images from Imgur.

![Java](https://img.shields.io/badge/Java-17+-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## Features

- **Random Image Generation** - Fetches random images from Imgur's vast collection
- **Session History** - View all previously fetched images from the current session
- **Download Images** - Save any image (current or from history) to your computer
- **Dark/Light Themes** - Toggle between dark and light modes with blue accent buttons
- **Zoom Controls** - Mouse wheel zoom and sidebar controls (Fit, 1:1, +, -)
- **Responsive Layout** - Adapts to different window sizes

## Screenshots

The application features:
- Clean dark and light themes
- Blue accent buttons
- Smooth image transitions
- Horizontal scrolling history gallery
- Zoom sidebar controls

## Requirements

- **Java 17** or higher (latest version recommended)
- **Maven 3.6+** (only needed for building from source)

## Quick Start

If you just want to run the application:

1. Download and install the latest version of Java from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)
2. Download `random-image-generator-1.0.0.jar` from the releases
3. Double-click the JAR file, or run:
   ```bash
   java -jar random-image-generator-1.0.0.jar
   ```

## Building from Source

### Build with Maven

```bash
# Clone the repository
cd Random-Imgur

# Build the project
mvn clean package

# The JAR file will be created in the target directory
```

### Running the Application

#### Option 1: Run with Maven
```bash
mvn javafx:run
```

#### Option 2: Run the JAR file
```bash
java -jar target/random-image-generator-1.0.0.jar
```

> **Note:** If you encounter issues running the JAR directly, you may need to use the JavaFX runtime:
> ```bash
> java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.swing -jar target/random-image-generator-1.0.0.jar
> ```

## Usage

1. **Generate Random Image** - Click the "Generate Random" button to fetch a random image
2. **View Image** - The image is displayed in the center panel
3. **Download** - Click "Download" to save the current image
4. **History** - Previously fetched images appear in the bottom gallery
5. **View Past Image** - Click any thumbnail or the "View" button to view a previous image
6. **Download from History** - Click the "Save" button on any thumbnail to download it
7. **Clear History** - Use the "Clear" button to reset the session history
8. **Toggle Theme** - Click the theme button to switch between dark and light modes
9. **Zoom** - Use the mouse wheel or sidebar buttons to zoom in/out

## Project Structure

```
Random-Imgur/
├── pom.xml                              # Maven configuration
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java         # Java module definition
│       │   └── com/randomimgur/
│       │       ├── Main.java            # Application entry point
│       │       ├── Launcher.java        # JAR launcher (workaround)
│       │       ├── RandomImgurApp.java  # Main UI class
│       │       └── ImageService.java    # Image fetching service
│       └── resources/
│           ├── images/                  # Application icon
│           └── styles/
│               └── main.css             # Dark/Light theme styles
└── README.md
```

## Technical Details

- **JavaFX** - Modern UI toolkit for Java
- **HttpClient** - Java 11+ HTTP client for fetching images
- **CSS Styling** - Custom dark and light themes with blue accents
- **Async Operations** - Non-blocking image fetching
- **Maven Shade Plugin** - Creates executable fat JAR

## Credits

Created by Aryeh Bloom and Jack Seigerman.

## License

MIT License - Feel free to use and modify!
