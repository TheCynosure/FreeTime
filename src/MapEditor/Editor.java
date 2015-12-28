package MapEditor;

import Loader.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Jack on 12/24/2015.
 * Implements a basic image placing screen to create map files.
 */
public class Editor extends JPanel implements MouseListener, MouseMotionListener, KeyListener{

    private boolean mouse1Down, mouse3Down;
    //EditorBoard 0 is the foreground and 1 is the backgroundBoard.
    private int[][][] editorBoards;
    //Options that allows us to save and do cool things.
    private Options options;
    //If true then the editor will rendering the scene.
    private boolean rendering = false;

    //Editor size is the amount of blocks shown in the editor.
    //Scale amount is the size of the blocks relative to the screen.
    private int editorSize, scaleAmount;

    public Editor(int editorSize) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.editorSize = editorSize;
        //2 meaning there is 2 layers. Foreground and Background.
        editorBoards = new int[2][editorSize][editorSize];
        editorBoards = clearTheMap(editorBoards);
        //Creating the options
        options = new Options();
    }

    private int[][][] clearTheMap(int[][][] map_array) {
        //Making the board all -1 or nothing.
        for(int k = 0; k < map_array.length; k++) {
            for (int i = 0; i < map_array[k].length; i++) {
                for (int j = 0; j < map_array[k][0].length; j++) {
                    map_array[k][i][j] = -1;
                }
            }
        }
        return map_array;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if(!rendering) {
            paintEditor(graphics, editorBoards[MenuManager.getCurrentLayer()]);
        } else {
            paintEditor(graphics, editorBoards[1]);
            graphics.setColor(new Color(0,0,0,150));
            graphics.fillRect(0,0, getWidth(), getHeight());
            paintEditor(graphics, editorBoards[0]);

        }
    }

    public void paintEditor(Graphics graphics, int[][] editorBoard) {
        //Scales the size of the blocks / tiles to the size of the window.
        scaleAmount = getHeight() / editorSize;
        //Loops through the editor and draws the image using the index of the image stored in the map array.
        for(int r = 0; r < editorSize; r++) {
            for(int c = 0; c < editorSize; c++) {
                //Image index's start at zero.
                if(editorBoard[r][c] >= 0) {
                    //If there is a image index then draw it.
                    graphics.drawImage(ResourceManager.getImage(1, editorBoard[r][c]).getScaledInstance(scaleAmount, scaleAmount, Image.SCALE_SMOOTH), c * scaleAmount, r * scaleAmount, null);
                }
                else {
                    //If there it is a blank square then just draw a rect outline.
                    graphics.drawRect(c * scaleAmount, r * scaleAmount, scaleAmount, scaleAmount);
                }
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!rendering) {
            //Gets the current row and column that the user clicked on.
            //Scale amount is the size of the bricks.
            //For the row it adds in the size of the title bar.
            int r = (e.getY() + getInsets().top) / scaleAmount;
            int c = (e.getX()) / scaleAmount;
            //Only start editing tiles in the array if the row and column are in bound.
            if (r >= 0 && c >= 0 && r < editorBoards[MenuManager.getCurrentLayer()].length && c < editorBoards[MenuManager.getCurrentLayer()][0].length) {
                //If it is a left click than it places a tile.
                if (e.getButton() == MouseEvent.BUTTON1) {
                    editorBoards[MenuManager.getCurrentLayer()][r][c] = MenuManager.getCurrentImage();
                    mouse1Down = true;
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    //If it is a right click than it deletes the tile.
                    editorBoards[MenuManager.getCurrentLayer()][r][c] = -1;
                    mouse3Down = true;
                }
                //Then repaints so that the changes will show up.
                repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!rendering) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                mouse1Down = false;
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                mouse3Down = false;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!rendering) {
            //Gets the current row and column that the user clicked on.
            //Scale amount is the size of the bricks.
            //For the row it adds in the size of the title bar.
            int r = (e.getY() + getInsets().top) / scaleAmount;
            int c = (e.getX()) / scaleAmount;
            //Only start editing tiles in the array if the row and column are in bound.
            if (r >= 0 && c >= 0 && r < editorBoards[MenuManager.getCurrentLayer()].length && c < editorBoards[MenuManager.getCurrentLayer()][0].length) {
                //If the user has mouse button 1 down then place tiles.
                if (mouse1Down) {
                    editorBoards[MenuManager.getCurrentLayer()][r][c] = MenuManager.getCurrentImage();
                } else if (mouse3Down) {
                    //Else delete the tile at those locations.
                    editorBoards[MenuManager.getCurrentLayer()][r][c] = -1;
                }
                //Then repaints so that the changes will show up.
                repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //If it was the s key then save the background and foreground to an array.
        if(e.getKeyChar() == 's') {
            options.save(editorBoards);
        }
        //Will rendering the scene or stop rendering.
        if(e.getKeyChar() == 'r') {
            if(rendering) {
                System.out.println("Rendering stopped");
                rendering = false;
            } else {
                System.out.println("Rendering");
                rendering = true;
            }
            //Let us see the changes
            repaint();
        }
        //Clear the board
        if(e.getKeyChar() == 'c') {
            //Make sure the user wants this.
            int status = JOptionPane.showConfirmDialog(null, "Do you really want to clear everything?");
            if(status == JOptionPane.YES_OPTION) {
                editorBoards = clearTheMap(editorBoards);
                //Let us see the changes
                repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
