package MapEditor;

/**
 * Created by Jack on 12/26/2015.
 */
public class MenuManager {
    private static int currentImage = 0;

    public static int getCurrentImage() {
        return currentImage;
    }

    public static void setCurrentImage(int currentImage) {
        MenuManager.currentImage = currentImage;
    }
}
