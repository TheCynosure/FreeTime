package Game;

import Loader.ResourceManager;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Jack on 12/23/2015.
 * Todo: Collision that is created with the map.
 */
public class GameState extends State {

    private NinjaCharacter ninjaCharacter;
    private PlayerController playerController;
    private int frameNum;
    private LevelManager levelManager;

    public GameState() {
        ninjaCharacter = new NinjaCharacter(200, 300, 0, 0);
        playerController = new PlayerController(ninjaCharacter);
        frameNum = 0;
        levelManager = new LevelManager(ResourceManager.loadArray(2, "Levels/Level1"), 1);
    }

    @Override
    public void paint(Graphics g, int width, int height) {
        //Make the  ninja proportional to about 1 brick size
        levelManager.paintLevel(g, width, height);
        playerController.characterDrawMode(g, height / levelManager.getRowNum());
//        ninjaCharacter.draw(g, height / levelManager.getRowNum());
    }

    public void update(int width, int height) {
        ninjaCharacter.move();
        playerController.checkCollision(levelManager, height / levelManager.getRowNum());
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

    @Override
    public void componentResized(ComponentEvent e, int width, int height) {
        levelManager.initLevel(width, height);
        levelManager.initCollidableObjects(0, height / levelManager.getRowNum());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
