package Game;

import Loader.ResourceManager;

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
        graphics.drawRect(getX() - scale * 2, getY() - scale * 2, scale * 5, scale * 5);
        draw(graphics, scale);
    }

    public void checkCollision(Rectangle[][] collidable_objects, int scale_amount) {
        lastCollided.clear();
        //only check the objects that are within a 5x5 square around the player.
        //Num of collision from under the player
        int tileUnder = 0;
        int player_column = getX() / scale_amount;
        int player_row = getY() / scale_amount;
        for(int r = player_row - 2; r <= player_row + 2; r++) {
            for(int c = player_column - 2; c <= player_column + 2; c++) {
                //Make sure we are checking only the columns and rows that are inbound
                if(r >= 0 && c >= 0 && r < collidable_objects.length && c < collidable_objects[0].length && collidable_objects[r][c] != null) {
                    //If there was a collision with one of the sides...
                    if(checkSingleCollision(collidable_objects[r][c], scale_amount)) {
                        lastCollided.add(collidable_objects[r][c]);
                        collisionDetected(collidable_objects[r][c], scale_amount);
                    }
                    if(r == player_row + 1 && player_column == c) {
                        tileUnder++;
                    }
                }
            }
        }
        if(tileUnder == 0) {
            //If there is no tiles under the player than apply gravity.
            falling = true;
        }
    }

    public void collisionDetected(Rectangle object, int scale_amount) {
        //Will change the characters movement based on the collision.
        if(vx > 0) {
            x = object.x - scale_amount;
            vx = 0;
        } else if(vx < 0) {
            x = object.x + scale_amount;
            vx = 0;
        }
        if(vy > 0) {
            y = object.y - scale_amount;
            vy = 0;
            falling = false;
        } else if(vy < 0) {
            y = object.y + scale_amount;
            vy = 0;
            falling = true;
        }
    }

    //Will check if the player is colliding with a single object.
    private boolean checkSingleCollision(Rectangle object, int scale_amount) {
        if(object.intersects(new Rectangle(getX(), getY(), scale_amount, scale_amount))) {
            return true;
        }
        return false;
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
