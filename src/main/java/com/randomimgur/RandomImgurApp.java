package com.randomimgur;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application UI for the Random Imgur Viewer.
 */
public class RandomImgurApp {
    
    private final Stage stage;
    private final BorderPane root;
    private final ImageView mainImageView;
    private final HBox historyContainer;
    private final ScrollPane historyScrollPane;
    private final ScrollPane imageScrollPane;
    private final Label statusLabel;
    private final Label imageIdLabel;
    private final Label zoomLabel;
    private final ProgressIndicator loadingIndicator;
    private final Button generateButton;
    private final Button downloadButton;
    private final ToggleButton themeToggle;
    
    private final DoubleProperty zoomLevel = new SimpleDoubleProperty(1.0);
    private boolean isDarkMode = true;
    
    private final List<ImageHistoryItem> imageHistory = new ArrayList<>();
    private final ImageService imageService = new ImageService();
    private ImageHistoryItem currentImage = null;
    
    public RandomImgurApp(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();
        root.getStyleClass().addAll("root-pane", "dark-mode");
        
        // Create main image view
        mainImageView = new ImageView();
        mainImageView.setPreserveRatio(true);
        mainImageView.setSmooth(true);
        mainImageView.getStyleClass().add("main-image");
        
        // Create scrollable image container
        imageScrollPane = new ScrollPane();
        imageScrollPane.getStyleClass().add("image-scroll-pane");
        imageScrollPane.setPannable(true);
        
        // Create loading indicator
        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false);
        loadingIndicator.getStyleClass().add("loading-indicator");
        
        // Create status and ID labels
        statusLabel = new Label("Click 'Generate' to fetch a random image");
        statusLabel.getStyleClass().add("status-label");
        
        imageIdLabel = new Label("");
        imageIdLabel.getStyleClass().add("image-id-label");
        
        zoomLabel = new Label("100%");
        zoomLabel.getStyleClass().add("zoom-label");
        
        // Create buttons
        generateButton = createStyledButton("Generate Random", "primary-button");
        downloadButton = createStyledButton("Download", "primary-button");
        downloadButton.setDisable(true);
        
        // Create theme toggle
        themeToggle = new ToggleButton("Dark Mode");
        themeToggle.setSelected(true);
        themeToggle.getStyleClass().add("theme-toggle");
        themeToggle.setOnAction(e -> toggleTheme());
        
        // Create history container
        historyContainer = new HBox(10);
        historyContainer.setAlignment(Pos.CENTER_LEFT);
        historyContainer.setPadding(new Insets(10));
        historyContainer.getStyleClass().add("history-container");
        
        historyScrollPane = new ScrollPane(historyContainer);
        historyScrollPane.setFitToHeight(true);
        historyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        historyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        historyScrollPane.getStyleClass().add("history-scroll-pane");
        
        // Setup layout
        setupLayout();
        
        // Setup event handlers
        setupEventHandlers();
    }
    
    private Button createStyledButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().addAll("styled-button", styleClass);
        button.setCursor(javafx.scene.Cursor.HAND);
        
        // Add hover animation
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), button);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        
        button.setOnMouseEntered(e -> scaleUp.playFromStart());
        button.setOnMouseExited(e -> scaleDown.playFromStart());
        
        return button;
    }
    
    private void setupLayout() {
        // Header with theme toggle - use StackPane for true centering
        StackPane header = new StackPane();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.getStyleClass().add("header");
        
        VBox titleBox = new VBox(2);
        titleBox.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Random Image Generator");
        titleLabel.getStyleClass().add("title-label");
        
        Label subtitleLabel = new Label("Discover random images from the depths of Imgur");
        subtitleLabel.getStyleClass().add("subtitle-label");
        
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);
        
        // Title is always centered, theme toggle on the right
        StackPane.setAlignment(titleBox, Pos.CENTER);
        StackPane.setAlignment(themeToggle, Pos.CENTER_RIGHT);
        header.getChildren().addAll(titleBox, themeToggle);
        
        // Center content - Main image area with scroll
        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-container");
        imageContainer.setAlignment(Pos.CENTER);
        
        // Wrap image in a centered container
        StackPane imageWrapper = new StackPane(mainImageView);
        imageWrapper.setAlignment(Pos.CENTER);
        
        imageScrollPane.setContent(imageWrapper);
        imageScrollPane.setFitToWidth(true);
        imageScrollPane.setFitToHeight(true);
        imageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        imageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        // Zoom binding
        mainImageView.scaleXProperty().bind(zoomLevel);
        mainImageView.scaleYProperty().bind(zoomLevel);
        
        // Mouse scroll for zoom (no Ctrl needed)
        imageScrollPane.setOnScroll(e -> {
            e.consume();
            double delta = e.getDeltaY() > 0 ? 1.1 : 0.9;
            double newZoom = zoomLevel.get() * delta;
            newZoom = Math.max(0.25, Math.min(5.0, newZoom));
            zoomLevel.set(newZoom);
            zoomLabel.setText(String.format("%.0f%%", newZoom * 100));
        });
        
        VBox placeholderBox = new VBox(20);
        placeholderBox.setAlignment(Pos.CENTER);
        placeholderBox.getStyleClass().add("placeholder-box");
        
        Label placeholderIcon = new Label("[Image]");
        placeholderIcon.setStyle("-fx-font-size: 48px; -fx-opacity: 0.5;");
        
        Label placeholderText = new Label("No image loaded");
        placeholderText.getStyleClass().add("placeholder-text");
        
        placeholderBox.getChildren().addAll(placeholderIcon, placeholderText);
        
        // Zoom sidebar (vertical, on right side)
        VBox zoomSidebar = new VBox(8);
        zoomSidebar.setAlignment(Pos.CENTER);
        zoomSidebar.setPadding(new Insets(10, 8, 10, 8));
        zoomSidebar.getStyleClass().add("zoom-sidebar");
        
        Button zoomInBtn = new Button("+");
        zoomInBtn.getStyleClass().add("zoom-button");
        zoomInBtn.setOnAction(e -> {
            double newZoom = Math.min(5.0, zoomLevel.get() + 0.1);
            zoomLevel.set(newZoom);
            zoomLabel.setText(String.format("%.0f%%", newZoom * 100));
        });
        
        Button zoomOutBtn = new Button("−");
        zoomOutBtn.getStyleClass().add("zoom-button");
        zoomOutBtn.setOnAction(e -> {
            double newZoom = Math.max(0.1, zoomLevel.get() - 0.1);
            zoomLevel.set(newZoom);
            zoomLabel.setText(String.format("%.0f%%", newZoom * 100));
        });
        
        Button fitBtn = new Button("Fit");
        fitBtn.getStyleClass().add("zoom-button");
        fitBtn.setOnAction(e -> fitImageToContainer());
        
        Button actualSizeBtn = new Button("1:1");
        actualSizeBtn.getStyleClass().add("zoom-button");
        actualSizeBtn.setOnAction(e -> {
            zoomLevel.set(1.0);
            zoomLabel.setText("100%");
            mainImageView.setFitWidth(0);
            mainImageView.setFitHeight(0);
        });
        
        zoomLabel.getStyleClass().add("zoom-label-sidebar");
        
        zoomSidebar.getChildren().addAll(zoomInBtn, zoomLabel, zoomOutBtn, new Separator(), fitBtn, actualSizeBtn);
        
        // Image area with sidebar
        BorderPane imageAreaPane = new BorderPane();
        imageAreaPane.setCenter(imageContainer);
        imageAreaPane.setRight(zoomSidebar);
        
        imageContainer.getChildren().addAll(placeholderBox, imageScrollPane, loadingIndicator);
        
        // Control panel (without zoom controls now)
        HBox controlPanel = new HBox(20);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setPadding(new Insets(12));
        controlPanel.getStyleClass().add("control-panel");
        
        VBox statusBox = new VBox(3);
        statusBox.setAlignment(Pos.CENTER);
        statusBox.getChildren().addAll(statusLabel, imageIdLabel);
        
        controlPanel.getChildren().addAll(generateButton, statusBox, downloadButton);
        
        // Main center area
        VBox centerBox = new VBox(8);
        centerBox.getStyleClass().add("center-box");
        centerBox.setPadding(new Insets(10));
        VBox.setVgrow(imageAreaPane, Priority.ALWAYS);
        centerBox.getChildren().addAll(imageAreaPane, controlPanel);
        
        // History panel at bottom
        VBox historyPanel = new VBox(8);
        historyPanel.getStyleClass().add("history-panel");
        historyPanel.setPadding(new Insets(12, 12, 12, 12));
        
        HBox historyHeader = new HBox(10);
        historyHeader.setAlignment(Pos.CENTER_LEFT);
        
        Label historyTitle = new Label("Session History");
        historyTitle.getStyleClass().add("history-title");
        
        Label historyCount = new Label("(0 images)");
        historyCount.getStyleClass().add("history-count");
        historyCount.setId("historyCount");
        
        Button clearHistoryButton = new Button("Clear");
        clearHistoryButton.getStyleClass().add("clear-button");
        clearHistoryButton.setOnAction(e -> clearHistory());
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        historyHeader.getChildren().addAll(historyTitle, historyCount, spacer, clearHistoryButton);
        
        historyScrollPane.setPrefHeight(120);
        historyScrollPane.setMinHeight(120);
        historyScrollPane.setMaxHeight(120);
        
        historyPanel.getChildren().addAll(historyHeader, historyScrollPane);
        
        // Wrapper to add margin above and below the history panel
        VBox bottomWrapper = new VBox(historyPanel);
        bottomWrapper.setPadding(new Insets(15, 0, 15, 0));
        
        // Assemble layout
        root.setTop(header);
        root.setCenter(centerBox);
        root.setBottom(bottomWrapper);
    }
    
    private void setupEventHandlers() {
        generateButton.setOnAction(e -> generateRandomImage());
        downloadButton.setOnAction(e -> downloadCurrentImage());
    }
    
    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            root.getStyleClass().remove("light-mode");
            root.getStyleClass().add("dark-mode");
            themeToggle.setText("Dark Mode");
        } else {
            root.getStyleClass().remove("dark-mode");
            root.getStyleClass().add("light-mode");
            themeToggle.setText("Light Mode");
        }
    }
    
    private void fitImageToContainer() {
        if (mainImageView.getImage() == null) return;
        
        double imageWidth = mainImageView.getImage().getWidth();
        double imageHeight = mainImageView.getImage().getHeight();
        
        if (imageWidth <= 0 || imageHeight <= 0) return;
        
        // Get container size
        double containerWidth = imageScrollPane.getWidth() - 40;
        double containerHeight = imageScrollPane.getHeight() - 40;
        
        if (containerWidth <= 0 || containerHeight <= 0) {
            // Fallback to viewport bounds
            Bounds viewportBounds = imageScrollPane.getViewportBounds();
            containerWidth = viewportBounds.getWidth() - 40;
            containerHeight = viewportBounds.getHeight() - 40;
        }
        
        if (containerWidth <= 0 || containerHeight <= 0) return;
        
        // Calculate scale to fit image in container
        double scaleX = containerWidth / imageWidth;
        double scaleY = containerHeight / imageHeight;
        double scale = Math.min(scaleX, scaleY);
        
        // Allow slight upscaling for small images, but cap at 100%
        scale = Math.min(scale, 1.0);
        
        // Set the image to its natural size, then scale it
        mainImageView.setFitWidth(imageWidth);
        mainImageView.setFitHeight(imageHeight);
        
        zoomLevel.set(scale);
        zoomLabel.setText(String.format("%.0f%%", scale * 100));
        
        // Center scroll position
        imageScrollPane.setHvalue(0.5);
        imageScrollPane.setVvalue(0.5);
    }
    
    private void generateRandomImage() {
        setLoading(true);
        statusLabel.setText("Searching for a random image...");
        
        imageService.fetchRandomImageAsync(result -> {
            Platform.runLater(() -> {
                if (result != null) {
                    displayImage(result);
                } else {
                    statusLabel.setText("Failed to fetch image. Try again!");
                }
                setLoading(false);
            });
        });
    }
    
    private void displayImage(ImageService.ImageResult result) {
        // Fade out current image
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), mainImageView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            mainImageView.setImage(result.image());
            mainImageView.setFitWidth(result.image().getWidth());
            mainImageView.setFitHeight(result.image().getHeight());
            
            // Fit to container
            Platform.runLater(this::fitImageToContainer);
            
            // Fade in new image
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), mainImageView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
        
        // Update status
        statusLabel.setText("Image loaded successfully!");
        imageIdLabel.setText("ID: " + result.id() + " | " + (int)result.image().getWidth() + "x" + (int)result.image().getHeight());
        downloadButton.setDisable(false);
        
        // Add to history
        ImageHistoryItem historyItem = new ImageHistoryItem(result.id(), result.image(), result.url());
        currentImage = historyItem;
        addToHistory(historyItem);
    }
    
    private void addToHistory(ImageHistoryItem item) {
        imageHistory.add(0, item);
        
        // Create thumbnail
        VBox thumbnailBox = createThumbnail(item);
        historyContainer.getChildren().add(0, thumbnailBox);
        
        // Update count
        Label countLabel = (Label) root.lookup("#historyCount");
        if (countLabel != null) {
            countLabel.setText("(" + imageHistory.size() + " images)");
        }
        
        // Animate in
        thumbnailBox.setOpacity(0);
        thumbnailBox.setTranslateY(20);
        
        FadeTransition fade = new FadeTransition(Duration.millis(300), thumbnailBox);
        fade.setFromValue(0);
        fade.setToValue(1);
        
        TranslateTransition translate = new TranslateTransition(Duration.millis(300), thumbnailBox);
        translate.setFromY(20);
        translate.setToY(0);
        
        ParallelTransition animation = new ParallelTransition(fade, translate);
        animation.play();
    }
    
    private VBox createThumbnail(ImageHistoryItem item) {
        VBox box = new VBox(3);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("thumbnail-box");
        box.setCursor(javafx.scene.Cursor.HAND);
        
        // Fixed size for consistent layout
        box.setMinWidth(85);
        box.setMaxWidth(85);
        box.setPrefWidth(85);
        box.setMinHeight(95);
        box.setMaxHeight(95);
        box.setPrefHeight(95);
        
        // Container for thumbnail with fixed size
        javafx.scene.layout.StackPane thumbnailContainer = new javafx.scene.layout.StackPane();
        thumbnailContainer.setMinSize(75, 50);
        thumbnailContainer.setMaxSize(75, 50);
        thumbnailContainer.setPrefSize(75, 50);
        
        ImageView thumbnail = new ImageView(item.image());
        thumbnail.setFitWidth(75);
        thumbnail.setFitHeight(50);
        thumbnail.setPreserveRatio(true);
        thumbnail.setSmooth(true);
        thumbnail.getStyleClass().add("thumbnail-image");
        
        thumbnailContainer.getChildren().add(thumbnail);
        
        Label idLabel = new Label(item.id());
        idLabel.getStyleClass().add("thumbnail-id");
        
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button viewBtn = new Button("View");
        viewBtn.getStyleClass().add("thumbnail-button");
        viewBtn.setTooltip(new Tooltip("View this image"));
        viewBtn.setOnAction(e -> viewHistoryImage(item));
        
        Button downloadBtn = new Button("Save");
        downloadBtn.getStyleClass().add("thumbnail-button");
        downloadBtn.setTooltip(new Tooltip("Download this image"));
        downloadBtn.setOnAction(e -> downloadImage(item));
        
        buttonBox.getChildren().addAll(viewBtn, downloadBtn);
        
        box.getChildren().addAll(thumbnailContainer, idLabel, buttonBox);
        
        // Click to view
        thumbnailContainer.setOnMouseClicked(e -> viewHistoryImage(item));
        
        // Hover effect
        box.setOnMouseEntered(e -> box.getStyleClass().add("thumbnail-box-hover"));
        box.setOnMouseExited(e -> box.getStyleClass().remove("thumbnail-box-hover"));
        
        return box;
    }
    
    private void viewHistoryImage(ImageHistoryItem item) {
        currentImage = item;
        
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), mainImageView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            mainImageView.setImage(item.image());
            mainImageView.setFitWidth(item.image().getWidth());
            mainImageView.setFitHeight(item.image().getHeight());
            
            Platform.runLater(this::fitImageToContainer);
            
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), mainImageView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
        
        statusLabel.setText("Viewing image from history");
        imageIdLabel.setText("ID: " + item.id() + " | " + (int)item.image().getWidth() + "x" + (int)item.image().getHeight());
        downloadButton.setDisable(false);
    }
    
    private void downloadCurrentImage() {
        if (currentImage != null) {
            downloadImage(currentImage);
        }
    }
    
    private void downloadImage(ImageHistoryItem item) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialFileName(item.id() + ".png");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG Image", "*.png"),
            new FileChooser.ExtensionFilter("JPEG Image", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            boolean success = imageService.saveImage(item.image(), file);
            if (success) {
                statusLabel.setText("Image saved: " + file.getName());
                showNotification("Image saved successfully!", true);
            } else {
                statusLabel.setText("Failed to save image");
                showNotification("Failed to save image", false);
            }
        }
    }
    
    private void clearHistory() {
        if (imageHistory.isEmpty()) return;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear History");
        alert.setHeaderText("Clear all session history?");
        alert.setContentText("This will remove all " + imageHistory.size() + " images from the history.");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                imageHistory.clear();
                historyContainer.getChildren().clear();
                
                Label countLabel = (Label) root.lookup("#historyCount");
                if (countLabel != null) {
                    countLabel.setText("(0 images)");
                }
                
                statusLabel.setText("History cleared");
            }
        });
    }
    
    private void showNotification(String message, boolean success) {
        Label notification = new Label(message);
        notification.getStyleClass().add(success ? "notification-success" : "notification-error");
        notification.setTranslateY(-50);
        notification.setOpacity(0);
        
        StackPane container = (StackPane) mainImageView.getParent();
        container.getChildren().add(notification);
        StackPane.setAlignment(notification, Pos.TOP_CENTER);
        
        // Animate in
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), notification);
        slideIn.setToY(20);
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), notification);
        fadeIn.setToValue(1);
        
        ParallelTransition in = new ParallelTransition(slideIn, fadeIn);
        
        // Animate out after delay
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), notification);
        slideOut.setToY(-50);
        
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), notification);
        fadeOut.setToValue(0);
        
        ParallelTransition out = new ParallelTransition(slideOut, fadeOut);
        out.setOnFinished(e -> container.getChildren().remove(notification));
        
        SequentialTransition sequence = new SequentialTransition(in, pause, out);
        sequence.play();
    }
    
    private void setLoading(boolean loading) {
        loadingIndicator.setVisible(loading);
        generateButton.setDisable(loading);
        
        if (loading) {
            RotateTransition rotate = new RotateTransition(Duration.seconds(1), loadingIndicator);
            rotate.setByAngle(360);
            rotate.setCycleCount(Animation.INDEFINITE);
            rotate.play();
        }
    }
    
    public Pane getRoot() {
        return root;
    }
    
    /**
     * Record to hold image history items.
     */
    public record ImageHistoryItem(String id, Image image, String url) {}
}
