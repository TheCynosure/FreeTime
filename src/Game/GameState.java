package Game;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Jack on 12/23/2015.
 * TODO: Make gravity only effect the ninja if is not colliding
 * TODO: Work on map making <-- Block based or Free form?
 */
public class GameState extends State {

    private NinjaCharacter ninjaCharacter;
    private PlayerController playerController;
    private int frameNum;

    public GameState() {
        ninjaCharacter = new NinjaCharacter(0, 500, 0, 0);
        playerController = new PlayerController(ninjaCharacter);
        frameNum = 0;
    }

    @Override
    public void paint(Graphics g, int width, int height) {
        g.drawImage(ImageTools.createBackgroundImage(1, 18, width, height), 0, 0, null);
        ninjaCharacter.draw(g);
    }

    public void update() {
        ninjaCharacter.move();
        frameNum++;
        //Only increase the player animation once every 4 frames.
        if(frameNum % 4 == 0) {
            frameNum = 0;
            ninjaCharacter.increaseAnimation();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        playerController.keyPress(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        playerController.keyRelease(keyEvent);
    }
}
