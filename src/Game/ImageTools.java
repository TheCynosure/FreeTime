package Game;

import Loader.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jack on 12/23/2015.
 */
public class ImageTools {

    public static BufferedImage paintImageArray(int imageCategory, int imageId, int width, int height) {
        BufferedImage background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage backgroundImg = ResourceManager.getImage(imageCategory, imageId);
        for(int r = 0; r + backgroundImg.getHeight() <= height; r += backgroundImg.getHeight()) {
            for(int c = 0; c + backgroundImg.getWidth() <= width; c += backgroundImg.getWidth()) {
                background.getGraphics().drawImage(backgroundImg, c, r, null);
            }
        }
        return background;
    }

    public static BufferedImage paintImageArray(int[][] images, int width, int height, int image_category) {
        int scaleAmount = height / images.length;
        BufferedImage background_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //Loops through the editor and draws the image using the index of the image stored in the map array.
        for(int r = 0; r < images.length; r++) {
            for(int c = 0; c < images[0].length; c++) {
                //Image index's start at zero.
                if(images[r][c] >= 0) {
                    //If there is a image index then draw it.
                    background_img.getGraphics().drawImage(ResourceManager.getImage(image_category, images[r][c]).getScaledInstance(scaleAmount, scaleAmount, Image.SCALE_SMOOTH), c * scaleAmount, r * scaleAmount, null);
                }
            }
        }
        return background_img;
    }

    //TODO: Make the method of drawing the debug overlays more efficient.
    public static BufferedImage paintImageArrayDebug(int[][] images, int width, int height, int image_category, Point[] collided_objects) {
        int scaleAmount = height / images.length;
        BufferedImage background_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //Loops through the editor and draws the image using the index of the image stored in the map array.
        for(int r = 0; r < images.length; r++) {
            for(int c = 0; c < images[0].length; c++) {
                //Image index's start at zero.
                if(images[r][c] >= 0) {
                    //If there is a image index then draw it.
                    background_img.getGraphics().drawImage(ResourceManager.getImage(image_category, images[r][c]).getScaledInstance(scaleAmount, scaleAmount, Image.SCALE_SMOOTH), c * scaleAmount, r * scaleAmount, null);
                }
                if(isInArray(collided_objects, r, c)) {
                    //Drawing a debug overlay
                    background_img.getGraphics().fillRect(r * scaleAmount, c * scaleAmount, scaleAmount, scaleAmount);
                }
            }
        }
        return background_img;
    }

    //To check if a block has been collided with
    public static boolean isInArray(Point[] objects, int r, int c) {
        for(Point point: objects) {
            //Ampersands aren't showing up!
            //They are here though.
            if(point.x == c && point.y == r) {
                return true;
            }
        }
        return false;
    }

}
