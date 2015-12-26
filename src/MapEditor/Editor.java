package MapEditor;

import Loader.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Jack on 12/24/2015.
 * Implements a basic image placing screen to create map files.
 * TODO: Split everything into classes
 * TODO: Background and Foreground boards
 * TODO: Making map files
 * TODO: Collision box creating / exporting.
 */
public class Editor extends JPanel implements MouseListener{

    private int[][] editorBoard;

    //Editor size is the amount of blocks shown in the editor.
    //Scale amount is the size of the blocks relative to the screen.
    private int editorSize, scaleAmount;

    public Editor(int editorSize) {
        addMouseListener(this);
        this.editorSize = editorSize;
        editorBoard = new int[editorSize][editorSize];
        //Making the board all -1 or nothing.
        for(int i = 0; i < editorBoard.length; i++) {
            for(int j = 0; j < editorBoard[0].length; j++) {
                editorBoard[i][j] = -1;
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        scaleAmount = getHeight() / editorSize;
        for(int r = 0; r < editorSize; r++) {
            for(int c = 0; c < editorSize; c++) {
                if(editorBoard[r][c] > 0) {
                    //Drawing the image if it is not a nothing square!
                    graphics.drawImage(ResourceManager.getImage(1, editorBoard[r][c]).getScaledInstance(scaleAmount, scaleAmount, Image.SCALE_SMOOTH), c * scaleAmount, r * scaleAmount, null);
                }
                else {
                    graphics.drawRect(c * scaleAmount, r * scaleAmount, scaleAmount, scaleAmount);
                }
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int r = (e.getY() + getInsets().top) / scaleAmount;
        int c = (e.getX()) / scaleAmount;
        System.out.println(r + " " + c);
        if(e.getButton() == MouseEvent.BUTTON1) {
            editorBoard[r][c] = WindowInputBridge.getCurrentImage();
        } else if(e.getButton() == MouseEvent.BUTTON3){
            editorBoard[r][c] = -1;
        }
        repaint();
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
