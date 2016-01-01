package MapEditor;
import Loader.ResourceManager;

import javax.swing.*;
import java.io.File;

/**
 * Created by Jack on 12/24/2015.
 * The window for the creation of new maps.
 * Also implements the new editor JFrame with its custom JMenuBar.
 * //TODO: Try making different size maps.
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
        //Create the editor
        Editor editor = new Editor(16);
        //Add the editor's keyListener to the window.
        frame.addKeyListener(editor);
        //Add the editor into the window.
        frame.add(editor);
        //Let the user see it now that everything is done.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MapEditorWindow window = new MapEditorWindow();
    }
}
