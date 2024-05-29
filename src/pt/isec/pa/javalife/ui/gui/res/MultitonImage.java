package pt.isec.pa.javalife.ui.gui.res;

import javafx.scene.image.Image;

import java.util.HashMap;

public class MultitonImage {
    private MultitonImage() {}
    private static final HashMap<String, Image> models = new HashMap<>();

    public static Image getModel(String ImagnsName) {
        Image model = models.get(ImagnsName);
        if (model == null) {
            model = ImageManager.getImage(ImagnsName);;
            models.put(ImagnsName, model);
        }
        return model;
    }
}
