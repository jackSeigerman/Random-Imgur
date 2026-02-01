package com.randomimgur;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Service class for fetching and managing Imgur images.
 */
public class ImageService {
    
    private static final String IMGUR_BASE_URL = "https://i.imgur.com/";
    private static final String POSSIBLE_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ID_LENGTH = 5;
    private static final int INVALID_IMAGE_WIDTH = 161; // Imgur's "image removed" placeholder width
    private static final int MAX_RETRIES = 20;
    
    private final SecureRandom random = new SecureRandom();
    private final HttpClient httpClient;
    
    public ImageService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }
    
    /**
     * Generates a random Imgur ID string.
     */
    public String generateRandomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(POSSIBLE_CHARACTERS.length());
            sb.append(POSSIBLE_CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
    
    /**
     * Constructs the full Imgur URL for a given ID.
     */
    public String getImageUrl(String id) {
        return IMGUR_BASE_URL + id + ".jpg";
    }
    
    /**
     * Fetches a random valid image asynchronously.
     */
    public void fetchRandomImageAsync(Consumer<ImageResult> callback) {
        CompletableFuture.runAsync(() -> {
            ImageResult result = fetchRandomImageWithRetry(0);
            callback.accept(result);
        });
    }
    
    private ImageResult fetchRandomImageWithRetry(int attempt) {
        if (attempt >= MAX_RETRIES) {
            return null;
        }
        
        String id = generateRandomId();
        String url = getImageUrl(id);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .header("User-Agent", "RandomImgurViewer/1.0")
                    .GET()
                    .build();
            
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            
            if (response.statusCode() == 200) {
                byte[] imageData = response.body();
                
                // Create JavaFX image from bytes
                java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(imageData);
                Image image = new Image(bais);
                
                // Check if it's a valid image (not the "removed" placeholder)
                if (!image.isError() && image.getWidth() != INVALID_IMAGE_WIDTH) {
                    return new ImageResult(id, image, url);
                }
            }
            
            // Try again with a new ID
            return fetchRandomImageWithRetry(attempt + 1);
            
        } catch (IOException | InterruptedException e) {
            // Network error, try again
            return fetchRandomImageWithRetry(attempt + 1);
        }
    }
    
    /**
     * Fetches an image by its ID synchronously.
     */
    public Image fetchImageById(String id) {
        String url = getImageUrl(id);
        try {
            return new Image(url, true);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Saves a JavaFX Image to a file.
     */
    public boolean saveImage(Image image, File file) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            
            String fileName = file.getName().toLowerCase();
            String format = "png";
            
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                format = "jpg";
                // Convert to RGB for JPEG (remove alpha channel)
                BufferedImage rgbImage = new BufferedImage(
                        bufferedImage.getWidth(),
                        bufferedImage.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
                rgbImage.createGraphics().drawImage(bufferedImage, 0, 0, java.awt.Color.WHITE, null);
                bufferedImage = rgbImage;
            }
            
            return ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Record to hold image fetch results.
     */
    public record ImageResult(String id, Image image, String url) {}
}
