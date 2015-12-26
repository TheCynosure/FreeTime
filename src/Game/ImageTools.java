package Game;

import Loader.ResourceManager;

import java.awt.image.BufferedImage;

/**
 * Created by Jack on 12/23/2015.
 */
public class ImageTools {

    public static BufferedImage createBackgroundImage(int imageCategory, int imageId, int width, int height) {
        BufferedImage background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage backgroundImg = ResourceManager.getImage(imageCategory, imageId);
        for(int r = 0; r + backgroundImg.getHeight() <= height; r += backgroundImg.getHeight()) {
            for(int c = 0; c + backgroundImg.getWidth() <= width; c += backgroundImg.getWidth()) {
                background.getGraphics().drawImage(backgroundImg, c, r, null);
            }
        }
        return background;
    }

}
