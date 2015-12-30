package Game;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jack on 12/23/2015.
 */
public abstract class State implements KeyListener {
    public abstract void paint(Graphics g, int width, int height);

    public abstract void update(int width, int height);

    public abstract void keyTyped(KeyEvent keyEvent);

    public abstract void keyPressed(KeyEvent keyEvent);

    public abstract void keyReleased(KeyEvent keyEvent);

    public abstract void componentResized(ComponentEvent e, int width, int height);

    public abstract void componentMoved(ComponentEvent e);

    public abstract void componentShown(ComponentEvent e);

    public abstract void componentHidden(ComponentEvent e);
}
