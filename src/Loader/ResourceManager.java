package Loader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jack on 12/23/2015.
 * Must init the resource loader before use.
 */
public class ResourceManager {

    private static ArrayList<BufferedImage[]> images = new ArrayList<>();

    //Will call the init on all the folders in the root folder.
    public static void initMultiple(String rootResourceFolder, int spriteSheetWidth, int spriteSheetHeight) {
        File rootFolder = new File(rootResourceFolder);
        for(File file: rootFolder.listFiles()) {
            //If there is sub folders in this folder then run the init function on it.
            //This way we get all the images from the sub folders.
            if(file.isDirectory()) {
                //Gives the init function the path of the resource folder so it can search through it.
                init(file.getPath(), spriteSheetWidth, spriteSheetHeight);
            }
        }
    }

    public static void init(String resourceFolder, int spriteSheetWidth, int spriteSheetHeight) {
        //Gets the size of the folder with the images.
        File folder = new File(resourceFolder);
        BufferedImage[] tempImages = null;
        try {
            //Then inits the array to the amount of files in the folder.
            tempImages = new BufferedImage[folder.listFiles().length];
        } catch (NullPointerException e) {
            //Telling you if the folder is empty
            System.out.println("The resource folder you specified is empty.");
        }

        //Loops through and adds all of the loaded images into the image array.
        int index = 0;
        for(File image: folder.listFiles()) {
            try {
                if(image.getName().contains("sheet")) {
                    //If it is a sprite sheet then pass it to the splitSpriteSheet function.
                    //Give it the possible width and height of each images and the image of the sheet.
                    tempImages = splitSpriteSheet(ImageIO.read(image), spriteSheetWidth, spriteSheetHeight);
                } else {
                    //If it is not a sprite sheet then read it like a normal image.
                    //Reading and saving the image into the array of Temp Images.
                    tempImages[index] = ImageIO.read(image);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            index++;
        }
        images.add(tempImages);
    }

    //Returns a buffered image but finds that in the arrayList of image categories.
    //Then searches the array and gets the exact element you want.
    public static BufferedImage getImage(int imageCategory, int imageId) {
        if(imageCategory < images.size() && imageId < images.get(imageCategory).length) {
            return images.get(imageCategory)[imageId];
        }
        return images.get(imageCategory)[0];
    }

    //Splits a sprite sheet into smaller buffered images.
    public static BufferedImage[] splitSpriteSheet(BufferedImage spriteSheet, int spliceWidth, int spliceHeight) {
        BufferedImage[] tempImages = new BufferedImage[(spriteSheet.getWidth() / spliceWidth) * (spriteSheet.getHeight() / spliceHeight)];
        //Current index in tempImages
        int index = 0;
        //Looping through the rows and columns of the spriteSheet.
        for(int r = 0; r + spliceHeight <= spriteSheet.getHeight(); r += spliceHeight) {
            for(int c = 0; c + spliceWidth <= spriteSheet.getWidth(); c += spliceWidth) {
                //Adding in a subImage that is the size that the user requested.
                tempImages[index] = spriteSheet.getSubimage(c, r, spliceWidth, spliceHeight);
                index++;
            }
        }
        return tempImages;
    }

    public static int getImageCategorySize(int imageCategory) {
        return images.get(imageCategory).length;
    }
}
