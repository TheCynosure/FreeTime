package MapEditor;

import Loader.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Jack on 12/25/2015.
 */
public class SelectorWindow extends JPanel implements MouseListener{

    private int imageCategory, width, height;

    public SelectorWindow(int imageCategory) {
        this.imageCategory = imageCategory;
        JFrame frame = new JFrame("Tile selector");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(0,0, 200, 200);
        frame.add(this);
        frame.addMouseListener(this);
        width = ResourceManager.getImage(imageCategory, 0).getWidth();
        height = ResourceManager.getImage(imageCategory, 0).getHeight();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics graphics) {
        int currentRow = 0;
        int currentColumn = 0;
        //Loops through all the tiles in the current image Category.
        for(int i = 0; i < ResourceManager.getImageCategorySize(imageCategory); i++) {
            //Checks if it is still drawing within the window.
            if(currentColumn * width > getWidth()) {
                //If not will start a new row down one image height.
                currentRow++;
                currentColumn = 0;
            }
            graphics.drawImage(ResourceManager.getImage(imageCategory, i), currentColumn * width, currentRow * height, null);
            currentColumn++;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int columns_in_row = getWidth() / width;
        int rows = (int) Math.ceil(ResourceManager.getImageCategorySize(imageCategory) / (double)(columns_in_row));
        int x = getX() / width;
        int y = getY() / height;
        //If its all within bounds
        if(y <= rows && x <= columns_in_row) {
            //Also not the zero row!
            if (y > 0) {
                WindowInputBridge.setCurrentImage((y - 1) * columns_in_row + x);
            } else if(y == 0) {
                WindowInputBridge.setCurrentImage(x);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
