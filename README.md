# Random Imgur Viewer

A modern, sleek JavaFX application for discovering random images from Imgur.

![Java](https://img.shields.io/badge/Java-17+-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## Features

- 🎲 **Random Image Generation** - Fetches random images from Imgur's vast collection
- 📜 **Session History** - View all previously fetched images from the current session
- 💾 **Download Images** - Save any image (current or from history) to your computer
- 🎨 **Modern Dark UI** - Beautiful glassmorphism-inspired dark theme
- ✨ **Smooth Animations** - Polished transitions and hover effects
- 🖼️ **Responsive Layout** - Adapts to different window sizes

## Screenshots

The application features a modern dark theme with:
- Gradient backgrounds
- Glassmorphism effects
- Animated buttons
- Smooth image transitions
- Horizontal scrolling history gallery

## Requirements

- **Java 17** or higher
- **Maven 3.6+** (for building)

## Building the Application

### Build with Maven

```bash
# Clone the repository
cd random-imgur

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
java -jar target/random-imgur-1.0.0.jar
```

> **Note:** If you encounter issues running the JAR directly, you may need to use the JavaFX runtime:
> ```bash
> java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.swing -jar target/random-imgur-1.0.0.jar
> ```

## Usage

1. **Generate Random Image** - Click the "🎲 Generate Random" button to fetch a random image
2. **View Image** - The image is displayed in the center panel
3. **Download** - Click "💾 Download" to save the current image
4. **History** - Previously fetched images appear in the bottom gallery
5. **View Past Image** - Click any thumbnail or the 👁 button to view a previous image
6. **Download from History** - Click the 💾 button on any thumbnail to download it
7. **Clear History** - Use the "🗑️ Clear" button to reset the session history

## Project Structure

```
random-imgur/
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
│           └── styles/
│               └── main.css             # Modern dark theme styles
└── README.md
```

## Technical Details

- **JavaFX** - Modern UI toolkit for Java
- **HttpClient** - Java 11+ HTTP client for fetching images
- **CSS Styling** - Custom glassmorphism-inspired dark theme
- **Async Operations** - Non-blocking image fetching
- **Maven Shade Plugin** - Creates executable fat JAR

## Credits

Original concept by Aryeh Bloom and Jack Seigerman.
Modern UI redesign and rewrite.

## License

MIT License - feel free to use and modify!
