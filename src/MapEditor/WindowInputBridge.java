package MapEditor;

/**
 * Created by Jack on 12/25/2015.
 */
public class WindowInputBridge {
    public static int currentImage = 0;

    public static void setCurrentImage(int currentImageTemp) {
        currentImage = currentImageTemp;
    }

    public static int getCurrentImage() {
        return currentImage;
    }
}
