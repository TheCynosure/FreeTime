package Loader;

/**
 * Created by Jack on 12/28/2015.
 */
public class ResourceLoaderInfo {
    private int return_index;
    private int[][] return_array;

    public ResourceLoaderInfo(int return_index, int[][] return_array) {
        this.return_index = return_index;
        this.return_array = return_array;
    }

    public int[][] getReturn_array() {
        return return_array;
    }

    public void setReturn_array(int[][] return_array) {
        this.return_array = return_array;
    }

    public int getReturn_index() {
        return return_index;
    }

    public void setReturn_index(int return_index) {
        this.return_index = return_index;
    }
}
