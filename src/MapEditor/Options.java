package MapEditor;

import Loader.ResourceManager;

import javax.swing.*;

/**
 * Created by Jack on 12/27/2015.
 */
public class Options {
    public String promptUserForString(String label) {
        String name = JOptionPane.showInputDialog(null, label);
        name = validateString(name);
        return name;
    }

    private String validateString(String string) {
        if(string == null || string.isEmpty() || string.equals("")) {
            //Sassy Error response
            string = null;
        }
        return string;
    }

    public void save(int[][][] array) {
        System.out.print("Saving... ");
        String string = promptUserForString("Save As...");
        if(string != null) {
            int status = ResourceManager.arraySave(string, array);
            if (status >= 0) {
                System.out.println("Save successful");
            } else {
                System.out.println("Save failed");
            }
        } else {
            System.out.println("Save failed");
        }
    }
}
