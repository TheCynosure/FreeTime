package Game;

import Loader.ResourceManager;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 12/23/2015.
 * Base character class that includes basic functionality including:
 *        - Drawing
 *        - Movement
 */
public class Character {

    private int x, y, imageId, imageCategory;
    private double vx, vy;
    private ArrayList<Rectangle> lastCollided;
    private boolean falling = true;

    public Character(int x, int y, int imageCategory, int imageId) {
        this.x = x;
        this.y = y;
        this.imageId = imageId;
        this.imageCategory = imageCategory;
        vx = 0;
        vy = 0;
        lastCollided = new ArrayList<>();
    }

    public void move() {
        x += vx;
        y += vy;
        if(vy < 0) {
            falling = true;
        }
        if(falling) {
            gravity();
        }
    }

    /*
     Drawing methods.
        - Graphics --> What we use to do the drawing.
        - imageId --> The id of the specific image from Resource loader that is wanted.
        - scaleAmount --> The new width and height of the image.
     */

    public void draw(Graphics g) {
        //Draws the default to image that it started with.
        g.drawImage(ResourceManager.getImage(imageCategory, imageId), x, y, null);
    }

    public void draw(Graphics g, int imageId) {
        //Draws the specific image at this id, at the normal x and y.
        g.drawImage(ResourceManager.getImage(imageCategory, imageId), x, y, null);
    }

    public void draw(Graphics g, int imageId, int scaleAmount) {
        //Draws the image to the scale we told it.
        //Normal x and y, no image observer.
        g.drawImage(ResourceManager.getImage(imageCategory, imageId).getScaledInstance(scaleAmount, scaleAmount, Image.SCALE_DEFAULT), x, y, null);
    }

    public void drawBackward(Graphics g, int imageId, int scaleAmount) {
        //Draws the image to the scale we told it.
        //Adds the width to the X to compensate for the effect of flipping.
        //Draws at normal y and -scaleAmount meaning it will draw in the opposite direction... to the left.
        //Same height and no image observer.
        g.drawImage(ResourceManager.getImage(imageCategory, imageId).getScaledInstance(scaleAmount, scaleAmount, Image.SCALE_DEFAULT), x + scaleAmount, y, -scaleAmount, scaleAmount, null);
    }

    /*
      End of the drawing methods!
     */

    public void gravity() {
        setVy(getVy() + 1);
    }

    public void debugDraw(Graphics graphics, int scale) {
        graphics.setColor(Color.CYAN);
        graphics.drawRect(getX() - scale, getY() - scale, scale * 3, scale * 3);
        draw(graphics, scale);
    }

    public void checkCollision(Rectangle[][] collidable_objects, int scale_amount) {
        lastCollided.clear();
        int tileUnder = 0;
        int player_column = getX() / scale_amount;
        int player_row = getY() / scale_amount;

        //TODO: Make a collision Method that works.

        if(player_row + 1 < collidable_objects.length && player_column - 1>= 0 && player_column + 1 < collidable_objects[0].length && collidable_objects[player_row + 1][player_column] != null && collidable_objects[player_row + 1][player_column - 1] != null) {
                        tileUnder++;
        }
        //Check the main area first, top, left, right, bottom.
        if(tileUnder == 0) {
            //If there is no tiles under the player than apply gravity.
            falling = true;
        }
    }

    /*
       These methods handle the movement after a certain type of collision.
     */

    public void handleTopCollision(Rectangle rectangle, int scale_amount) {
        setVy(0);
        falling = false;
        setY((int) (rectangle.getY() - scale_amount));
    }

    public void handleBottomCollision(Rectangle rectangle, int scale_amount) {
        setVy(0);
        falling = true;
        setY(rectangle.y + scale_amount);
    }

    public void handleLeftCollision(Rectangle rectangle, int scale_amount) {
        setVx(0);
        setX(rectangle.x - scale_amount);
    }

    public void handleRightCollision(Rectangle rectangle, int scale_amount) {
        setVx(0);
        setX(rectangle.x + scale_amount);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public ArrayList<Rectangle> getLastCollided() {
        return lastCollided;
    }
}
