package Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jack on 12/23/2015.
 */
public class LevelManager {
    private int[][][] map;
    private int image_category;
    private BufferedImage background, foreground;
    private Rectangle[][] collidable_objects;

    public LevelManager(int[][][] map, int image_category) {
        this.map = map;
        this.image_category = image_category;
        initCollidableObjects(0, 32);
    }

    //Will make an array of all the possible objects that the player could collide with.
    public void initCollidableObjects(int layer, int scale) {
        collidable_objects = new Rectangle[map[layer].length][map[layer][0].length];
        for(int r = 0; r < collidable_objects.length; r++) {
            for(int c = 0; c < collidable_objects[0].length; c++) {
                //Only init the rectangle if its a tile. Don't init blank spaces.
                if(map[layer][r][c] >= 0) {
                    collidable_objects[r][c] = new Rectangle(c * scale, r * scale, scale, scale);
                }
            }
        }
    }

    public int getRowNum() {
        return map[0].length;
    }

    public int getColumnNum() {
        return map[0][0].length;
    }

    public void initLevel(int width, int height) {
        background = ImageTools.paintImageArray(map[1], width, height, image_category);
        foreground = ImageTools.paintImageArray(map[0], width, height, image_category);
    }

    public void paintLevel(Graphics graphics, int width, int height) {
        graphics.drawImage(background, 0,0, null);
        //Used to separate background and foreground.
        graphics.setColor(new Color(0,0,0,150));
        graphics.fillRect(0,0,width,height);
        graphics.drawImage(foreground, 0,0, null);
    }

    public Rectangle[][] getCollidable_objects() {
        return collidable_objects;
    }
}
