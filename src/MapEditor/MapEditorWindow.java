package MapEditor;
import Loader.ResourceManager;

import javax.swing.*;

/**
 * Created by Jack on 12/24/2015.
 */
public class MapEditorWindow {
    public MapEditorWindow() {
        //Init the Resources
        //Must be done before the frame so they can be added to the menu.
        ResourceManager.initMultiple("Art", 16, 16);
        //Create a new frame
        EditorFrame frame = new EditorFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0,0,500,500);
        //Add all the images from above in the category 1 to a new menuBar.
        frame.addMenuBar(1);
        //Add the editor into the window.
        frame.add(new Editor(16));
        //Let the user see it now that everything is done.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MapEditorWindow window = new MapEditorWindow();
    }
}
