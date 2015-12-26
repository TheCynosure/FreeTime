package MapEditor;

import Loader.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jack on 12/26/2015.
 * Used to create the menu bar for the editor window!
 */
public class EditorFrame extends JFrame {

    //Uses the imageCategory so it can use put all images in that category into the list / menu.
    public void addMenuBar(int imageCategory) {
        //First make a menubar
        JMenuBar menuBar = new JMenuBar();
        //Then make the menu to be added to the bar.
        JMenu menu = new JMenu("Images");
        //Then add all the images that the user is using to the menu with JMenuItems.
        for(int i = 0; i < ResourceManager.getImageCategorySize(imageCategory); i++) {
            JMenuItem menuItem = new JMenuItem(i + "", new ImageIcon(ResourceManager.getImage(imageCategory, i)));
            //Adding in the actionListener that will set the current image to selected so we know later.
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //ActionCommand returns the index of the menuItem. --> Must parse because it comes as a string.
                    //Set the currently selected item to the index of the image.
                    MenuManager.setCurrentImage(Integer.parseInt(e.getActionCommand()));
                }
            });
            menu.add(menuItem);
        }
        //Add the menu to the menuBar so it will dropdown.
        menuBar.add(menu);
        //Add it all to the top of the frame / window.
        this.setJMenuBar(menuBar);
    }

}
