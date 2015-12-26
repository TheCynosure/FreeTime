package Game;

import java.awt.event.KeyEvent;

/**
 * Created by Jack on 12/23/2015.
 */
public class PlayerController {

    private Character character;

    public PlayerController(Character character) {
        this.character = character;
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
            if(character.getVy() == 0) {
                //Give them a Y acceleration.
                // - is up
                character.setVy(-5);
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
}
