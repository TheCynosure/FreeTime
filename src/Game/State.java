package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jack on 12/23/2015.
 */
public abstract class State implements KeyListener {
    public abstract void paint(Graphics g, int width, int height);

    public abstract void update();

    public abstract void keyTyped(KeyEvent keyEvent);

    public abstract void keyPressed(KeyEvent keyEvent);

    public abstract void keyReleased(KeyEvent keyEvent);
}
