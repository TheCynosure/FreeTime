package Game;

import Loader.ResourceManager;

import java.awt.*;

/**
 * Created by Jack on 12/23/2015.
 * Base character class that includes basic functionality including:
 *        - Drawing
 *        - Movement
 */
public class Character {

    private int x, y, imageId, imageCategory;
    private double vx, vy;

    public Character(int x, int y, int imageCategory, int imageId) {
        this.x = x;
        this.y = y;
        this.imageId = imageId;
        this.imageCategory = imageCategory;
        vx = 0;
        vy = 0;
    }

    public void move() {
        x += vx;
        y += vy;
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
}
