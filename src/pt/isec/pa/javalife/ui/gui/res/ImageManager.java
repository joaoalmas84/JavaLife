package pt.isec.pa.javalife.ui.gui.res;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Manages the loading and caching of images.
 * Provides methods for retrieving images from resources or external files.
 * Uses a HashMap to cache loaded images for faster access.
 * The class is designed with a private constructor and static methods only.
 * Depends on the Image class from JavaFX.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class ImageManager {
    /**
     * Private constructor to prevent instantiation of the class.
     */
    private ImageManager() {}

    private static final HashMap<String, Image> images = new HashMap<>();

    /**
     * Retrieves an image from the resources folder.
     * If the image is not already loaded, it loads the image from the specified filename and caches it.
     *
     * @param filename the filename of the image in the resources folder
     * @return the Image object, or null if the image cannot be loaded
     */
    public static Image getImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try (InputStream is = ImageManager.class.getResourceAsStream("images/"+filename)) {
                image = new Image(is);
                images.put(filename,image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

    /**
     * Retrieves an image from an external file.
     * If the image is not already loaded, it loads the image from the specified filename and caches it.
     *
     * @param filename the filename of the image in the external file system
     * @return the Image object, or null if the image cannot be loaded
     */
    public static Image getExternalImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try {
                image = new Image(filename);
                images.put(filename,image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

    /**
     * Removes an image from the cache.
     *
     * @param filename the filename of the image to be removed from the cache
     */
    public static void purgeImage(String filename) { images.remove(filename); }
}