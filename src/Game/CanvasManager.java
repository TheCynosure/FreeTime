package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Jack on 12/23/2015.
 * Invokes the current states methods.
 */
public class CanvasManager extends JPanel implements KeyListener, ComponentListener {

    public CanvasManager() {
        Timer timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //Calls the draw method of the current state
        StateManager.getCurrentState().paint(graphics, getWidth(), getHeight());
    }

    public void update() {
        StateManager.getCurrentState().update(getWidth(), getHeight());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        StateManager.getCurrentState().keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        StateManager.getCurrentState().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        StateManager.getCurrentState().keyReleased(e);
    }


    @Override
    public void componentResized(ComponentEvent e) {
        StateManager.getCurrentState().componentResized(e, getWidth(),  getHeight());
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
