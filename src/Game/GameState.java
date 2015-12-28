package Game;

import Loader.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by Jack on 12/23/2015.
 * Todo: Collision that is created with the map.
 * todo: Make the map class handle all loading and implementation of the map
 * todo: Make the map also save the category of the image.
 * todo: Make it so the editor can draw both background and foreground over each other so I can see what it looks like.
 */
public class GameState extends State {

    private NinjaCharacter ninjaCharacter;
    private PlayerController playerController;
    private int frameNum;
    private int[][][] map;

    public GameState() {
        ninjaCharacter = new NinjaCharacter(0, 500, 0, 0);
        playerController = new PlayerController(ninjaCharacter);
        frameNum = 0;
        map = ResourceManager.loadArray(2, "Testing");
    }

    @Override
    public void paint(Graphics g, int width, int height) {
        g.drawImage(ImageTools.createBackgroundImage(map[1], width, height), 0, 0, null);
        //Make the  ninja proportional to about 1 brick size
        ninjaCharacter.draw(g, height / map[0].length);
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
