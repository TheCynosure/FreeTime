package Game;

import Loader.ResourceManager;

import javax.swing.*;

/**
 * Created by Jack on 12/23/2015.
 */
public class Window {
    public Window(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0,0,500,500);

        //Adding in all the images into the resource loader
        ResourceManager.initMultiple("Art", 16, 16);
        //Adding in all the possible states for the game.
        GameState gameState = new GameState();
        StateManager.addState(gameState);

        CanvasManager canvasManager = new CanvasManager();
        frame.addComponentListener(canvasManager);
        frame.addKeyListener(canvasManager);
        frame.add(canvasManager);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Window window = new Window("Simple Game");
    }
}
