package Game;

import Loader.ResourceManager;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.scenario.effect.impl.sw.sse.SSEColorAdjustPeer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jack on 12/23/2015. TODO: Collision works for the top and bottom but not the left and the right.
 * Base character class that includes basic functionality including:
 *        - Drawing
 *        - Movement
 *        -Collision
 */
public class Character {

    private int x, y, imageId, imageCategory, lastX, lastY;
    private double vx, vy;
    private ArrayList<Rectangle> lastCollided;
    private boolean falling = true;

    public Character(int x, int y, int imageCategory, int imageId) {
        this.x = x;
        this.y = y;
        lastX = x;
        lastY = y;
        this.imageId = imageId;
        this.imageCategory = imageCategory;
        vx = 0;
        vy = 0;
        lastCollided = new ArrayList<>();
    }

    public void move() {
        lastX = x;
        lastY = y;
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
        int tile_under = 0;
        int player_column = getX() / scale_amount;
        int player_row = getY() / scale_amount;
        //First process out of map bounds collisions!
        //Left side of map
        if(getX() < 0) {
            setX(0);
        }
        //Right side of map
        else if(getX() + scale_amount > collidable_objects[0].length * scale_amount) {
            setX(collidable_objects[0].length * scale_amount - scale_amount);
        }
        //Top
        if(getY() < 0) {
            setY(0);
            falling = true;
            setVy(0);
        }
        //Wont check for bottom collisions. This way the player can fall out the bottom.
        for(int r = player_row - 1; r <= player_row + 1; r++) {
            for(int c = player_column - 1; c <= player_column + 1; c++) {
                if(r >= 0 && c >= 0 && r < collidable_objects.length && c < collidable_objects[0].length && collidable_objects[r][c] != null) {
                    //This is a tile then...
                    int return_val = checkAndHandleCollision(collidable_objects[r][c], scale_amount);
                    if(r == player_row + 1) {
                        tile_under += return_val;
                    }
                }
            }
        }
        //Check the main area first, top, left, right, bottom.
        if(tile_under == 0) {
            //If there is no tiles under the player than apply gravity.
            falling = true;
        }
    }

    private void reassignLastVals() {
        lastX = getX();
        lastY = getY();
    }

    public int checkAndHandleCollision(Rectangle collided_obj, int scale_amount) {
        //Check if there was a collision on any of the corners.
        //If there was then check what side it happened on!
        int collision_num = 0;
        if(collided_obj.contains(getX(), getY()) || collided_obj.contains(getX() + scale_amount, getY()) || collided_obj.contains(getX(), getY() + scale_amount) || collided_obj.contains(getX() + scale_amount, getY() + scale_amount) || collided_obj.contains(getX() + scale_amount / 2, getY() + scale_amount) || collided_obj.contains(getX() + scale_amount / 2, getY()) || collided_obj.contains(getX(), getY() + scale_amount / 2) || collided_obj.contains(getX() + scale_amount, getY() + scale_amount / 2)) {
            lastCollided.add(collided_obj);
            //Top
            if (lastY + scale_amount < collided_obj.y && getY() + scale_amount >= collided_obj.y) {
                handleTopCollision(collided_obj, scale_amount);
                reassignLastVals();
            }
            //Left
            if (lastX + scale_amount < collided_obj.x && getX() + scale_amount >= collided_obj.x) {
                handleLeftCollision(collided_obj, scale_amount);
                reassignLastVals();
            }
            //Right
            if (lastX > collided_obj.x + scale_amount && getX() <= collided_obj.x + scale_amount) {
                handleRightCollision(collided_obj, scale_amount);
                reassignLastVals();
            }
            //Bottom
            if (lastY > collided_obj.y + scale_amount && getY() <= collided_obj.y + scale_amount) {
                handleBottomCollision(collided_obj, scale_amount);
                reassignLastVals();
            }
            collision_num++;
        }
        return collision_num;
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
        setX(rectangle.x - scale_amount);
    }

    public void handleRightCollision(Rectangle rectangle, int scale_amount) {
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

    public boolean isFalling() { return falling; }
}
