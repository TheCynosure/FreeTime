package MapEditor;
import Loader.ResourceManager;

import javax.swing.*;

/**
 * Created by Jack on 12/24/2015.
 */
public class MapEditorWindow {
    public MapEditorWindow() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0,0,500,500);

        //Init the Resources
        ResourceManager.initMultiple("Art", 16, 16);
        //Make the Selector window
        SelectorWindow selectorWindow = new SelectorWindow(1);

        frame.add(new Editor(16));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MapEditorWindow window = new MapEditorWindow();
    }
}
