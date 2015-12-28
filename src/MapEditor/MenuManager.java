package MapEditor;

/**
 * Created by Jack on 12/26/2015.
 */
public class MenuManager {

    private static int currentLayer = 0;
    private static int currentImage = 0;

    public static int getCurrentImage() {
        return currentImage;
    }

    public static void setCurrentImage(int currentImage) {
        MenuManager.currentImage = currentImage;
    }

    public static int getCurrentLayer() {
        return currentLayer;
    }

    public static void setCurrentLayer(int currentLayer) {
        MenuManager.currentLayer = currentLayer;
    }
}
