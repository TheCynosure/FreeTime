package Loader;

import sun.plugin2.gluegen.runtime.BufferFactory;

import javax.imageio.ImageIO;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.io.*;
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

    /*
    Saving data methods.
    All save in the cvd format where the data is separated with commas.
    Like 1,2,4,0,2
     */

    //Will save in the current project directory.
    //Unless given another fileName
    //DOES NOT SUPPORT RAGGED ARRAYS
    //Returns 0 if successful and -1 if not.
    public static int arraySave(String fileName, int[][][] array) {
        File file = new File(fileName);
        try {
            if(file.createNewFile()) {
                PrintWriter outputFile = new PrintWriter(file);
                //For all the 2D arrays in the array.
                //First thing should be the array length so we can init it later.
                for(int arrayNum = 0; arrayNum < array.length; arrayNum++) {
                    //For all the rows in the 2D array.
                    for(int row = 0; row < array[0].length; row++) {
                        //For all the columns in the 2D array.
                        for(int column = 0; column < array[0][0].length; column++) {
                            outputFile.write(array[arrayNum][row][column] + ",");
                        }
                        //After every row, make a new line.
                        outputFile.write("\n");
                    }
                    outputFile.write("-\n");
                }
                //Once done clean up
                outputFile.close();
                return 0;
            } else {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int[][][] loadArray(int array_num, String file_name) {
        int[][][] loaded_array = null;
        try {
            //We need to find the number of rows and the number of columns of the arrays.
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            //First line in the array
            String line = reader.readLine();
            //To find the number of columns we will see how many elements there are on the first row.
            //This is the number of tiles --> also known as columns
            int columns = splitElements(line).length;
            //Next we will have a find the row num using the length of a the file.
            int rows = findRowNum(array_num, file_name);
            //Initing the array
            loaded_array = new int[array_num][rows][columns];
            //The first array will always be on line 0
            int start_index = 0;
            for(int i = 0; i < array_num; i++) {
                ResourceLoaderInfo info = loadSingleArray(start_index, rows, columns, file_name);
                loaded_array[i] = info.getReturn_array();
                start_index = info.getReturn_index();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loaded_array;
    }

    private static ResourceLoaderInfo loadSingleArray(int start_line, int row_num, int column_num, String file_name) {
        int[][] temp_array = new int[row_num][column_num];
        int i = start_line;
        //Buffered Reader so that we can read through the file.
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file_name));
            //Start the reader on the line that we were told to start on.
            bufferedReader.skip(i);
            String line = bufferedReader.readLine();
            while(line != null) {
                //Make sure to keep moving down the file.
                line = bufferedReader.readLine();
                //Keep track of what line we are on.
                i++;
                if(line.equals("-")) {
                    //If its not a full line but instead a divisor between arrays then stop recording the array.
                    break;
                } else {
                    temp_array[i - start_line] = splitElements(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Returns both the line number we are on and the array.
        //The line number will be used to load the next array.
        return new ResourceLoaderInfo(i, temp_array);
    }

    //Needs the number of arrays and the file.
    private static int findRowNum(int array_num, String file_name) {
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file_name));
            //Will jump to the last line in the array.
            lineNumberReader.skip(Long.MAX_VALUE);
            //The number of rows is:
            // (file max line num - (array_num - 1)) / array_num
            //- array_num will account for the - (divide / separator) between arrays and the / array_num will account
            // for their being more than one 2D array
            return (lineNumberReader.getLineNumber() - array_num) / array_num;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //The element are in a string and are separated by commas.
    //They must be separated.
    private static int[] splitElements(String elements) {
        //Every element in the array has a comma after it, so the num of commas is the num of elements.
        //String [] because this way I can concatenate numbers together.
        String[] element_array = new String[findAll(',', elements)];
        element_array = initStringArray(element_array);
        int current_element_index = 0;
        for(int i = 0; i < elements.length(); i++) {
            //If it is a comma then move on to the next element.
            // ^ Start a new element
            if(elements.charAt(i) == ',') {
                current_element_index++;
            } else {
                //If it didn't start a new element then add the num onto this one.
                element_array[current_element_index] += elements.charAt(i);
            }
        }
        return toIntArray(element_array);
    }

    //Just goes through a creates empty string obj's in all the index
    //So they are not just null.
    private static String[] initStringArray(String[] empty_array) {
        for(int i = 0; i < empty_array.length; i++) {
            empty_array[i] = "";
        }
        return empty_array;
    }

    //Used to find the number of times a certain char is in a string.
    private static int findAll(char character, String search_string) {
        int num_of_finds = 0;
        for(int i = 0; i < search_string.length(); i++) {
            if(search_string.charAt(i) == character) {
                num_of_finds++;
            }
        }
        return num_of_finds;
    }

    //Takes in a string array and parses all the strings into ints.
    private static int[] toIntArray(String[] elements) {
        int[] int_elements = new int[elements.length];
        for(int i = 0; i < elements.length; i++) {
            int_elements[i] = Integer.parseInt(elements[i]);
        }
        return int_elements;
    }
}
