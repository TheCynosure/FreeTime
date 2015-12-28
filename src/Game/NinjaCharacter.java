package Game;

import java.awt.*;
import java.lang.*;

/**
 * Created by Jack on 12/23/2015.
 */
public class NinjaCharacter extends Character {

    private int currentImage;
    private boolean lookingRight;

    public NinjaCharacter(int x, int y, int imageCategory, int imageId) {
        super(x, y, imageCategory, imageId);
        currentImage = 0;
    }

    @Override
    public void move() {
        if(getVy() < 0) {
            setVy(getVy() + 0.1);
        } else if(getVy() > 0) {
            setVy(0);
        }
        super.move();
    }

    public void increaseAnimation() {
        int animationIndex;
        //If moving in the Y direction then use the jump animation.
        if(getVy() != 0) {
            //Jump image is at 6
            currentImage = 6;
        } else {
            //Otherwise worry about the x movement!
            //If moving || VX is the acceleration!
            if (getVx() != 0) {
                //Then loop through the whole running animation.
                animationIndex = 5;
            } else {
                //Else loop through the idle animations || First two!
                animationIndex = 2;
            }
            currentImage++;
            //Check if we are within the legal animation boundaries.
            if (currentImage >= animationIndex) {
                currentImage = 0;
            }
        }
    }

    @Override
    public void draw(Graphics graphics, int scale) {
        //If the vx is positive
        if(getVx() > 0) {
            //Then make the character point right
            lookingRight = true;
        } else if(getVx() < 0) {
            //Else make him point left
            lookingRight = false;
        }

        //If there is movement in the Y direction then just use the jump animation.
        if(getVy() != 0) {
            if(lookingRight) {
                super.draw(graphics, currentImage, scale);
            } else {
                super.drawBackward(graphics, currentImage, scale);
            }
        } else {
            //Again only worrying about x movement if no movement in the Y direction.
            //If he is looking right
            if (lookingRight) {
                //Draw him looking right
                super.draw(graphics, currentImage, scale);
            } else {
                //Else draw him looking left
                super.drawBackward(graphics, currentImage, scale);
            }
        }
    }
}
