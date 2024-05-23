package pt.isec.pa.javalife.ui.gui.res;

import javafx.scene.Parent;

/**
 * Manages the application of CSS stylesheets to JavaFX parent nodes.
 * Provides a method for applying a CSS stylesheet to a parent node.
 * The class contains only static methods and cannot be instantiated.
 * Depends on the Parent class from JavaFX.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class CSSManager {
    /**
     * Private constructor to prevent instantiation of the class.
     */
    private CSSManager(){}

    /**
     * Applies a CSS stylesheet to a JavaFX parent node.
     * The stylesheet is loaded from the resources folder.
     *
     * @param parent the parent node to apply the CSS stylesheet to
     * @param filename the filename of the CSS stylesheet in the resources folder
     */
    public static void applyCSS(Parent parent, String filename) {
        var url = CSSManager.class.getResource("css/"+filename);
        if (url == null)
            return;
        String fileCSS = url.toExternalForm();
        parent.getStylesheets().add(fileCSS);
    }
}


