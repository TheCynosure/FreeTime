package Game;

import java.util.ArrayList;

/**
 * Created by Jack on 12/23/2015.
 * Holds all the possible screens or States and tells classes which one is the running one.
 * Must be at least one state in the manager to start the program, else crash.
 */
public class StateManager {
    private static int stateNum = 0;
    private static ArrayList<State> states = new ArrayList<>();

    public static void addState(State state) {
        states.add(state);
    }

    public static State getCurrentState() {
        return states.get(stateNum);
    }

    public void setCurrentState(int stateNum) {
        if(stateNum < states.size()) {
            this.stateNum = stateNum;
        }
    }
}
