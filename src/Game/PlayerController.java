package Game;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Jack on 12/23/2015.
 */
public class PlayerController {

    private Character character;
    private boolean collision_debug;
    private int jump_num = 0;

    public PlayerController(Character character) {
        this.character = character;
    }

    public void characterDrawMode(Graphics graphics, int scale) {
        if(collision_debug) {
            character.debugDraw(graphics, scale);
        } else {
            character.draw(graphics, scale);
        }
    }

    public void keyPress(KeyEvent keyEvent) {
        //Handling movement keys
        //Left
        if(keyEvent.getKeyChar() == 'a') {
            character.setVx(-5);
        }
        //Right
        if(keyEvent.getKeyChar() == 'd') {
            character.setVx(5);
        }

        //Jump --> Space keycode is 32
        if(keyEvent.getKeyCode() == 32) {
            //Only if the character has no Y acceleration will they be able to jump again.
            if(jump_num < 2) {
                //Give them a Y acceleration.
                // - is up
                jump_num++;
                character.setVy(-20);
            }
        }

        //Start the debug state:
        if(keyEvent.getKeyChar() == 'p') {
            //Switch the current debug state.
            if(collision_debug) {
                collision_debug = false;
            } else {
                collision_debug = true;
            }
        }
    }

    public void keyRelease(KeyEvent keyEvent) {
        //Handling movement keys
        //Left
        if(keyEvent.getKeyChar() == 'a') {
            character.setVx(0);
        }
        //Right
        if(keyEvent.getKeyChar() == 'd') {
            character.setVx(0);
        }
    }

    public boolean isCollisionDebugActive() {
        return collision_debug;
    }

    public void checkCollision(LevelManager levelManager, int scale_amount) {
        character.checkCollision(levelManager.getCollidable_objects(), scale_amount);
        if(!character.isFalling()) {
            //If is on the ground then restore jump num.
            jump_num = 0;
        }
    }

}
