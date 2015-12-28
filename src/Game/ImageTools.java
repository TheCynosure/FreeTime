package Game;

import Loader.ResourceManager;
import MapEditor.MenuManager;

import java.awt.*;
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

    public static BufferedImage createBackgroundImage(int[][] images, int width, int height) {
        int scaleAmount = height / images.length;
        BufferedImage background_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //Loops through the editor and draws the image using the index of the image stored in the map array.
        for(int r = 0; r < images.length; r++) {
            for(int c = 0; c < images[0].length; c++) {
                //Image index's start at zero.
                if(images[r][c] >= 0) {
                    //If there is a image index then draw it.
                    background_img.getGraphics().drawImage(ResourceManager.getImage(1, images[r][c]).getScaledInstance(scaleAmount, scaleAmount, Image.SCALE_SMOOTH), c * scaleAmount, r * scaleAmount, null);
                }
                else {
                    //If there it is a blank square then just draw a rect outline.
                    background_img.getGraphics().drawRect(c * scaleAmount, r * scaleAmount, scaleAmount, scaleAmount);
                }
            }
        }
        return background_img;
    }

}
