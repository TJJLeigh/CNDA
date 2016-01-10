package GameState;

import java.util.Observable;

/**
 * Created by Jack on 2016-01-10.
 */
public class GameStateInformation extends Observable {

    public void updateGameState(Object object){
        //for (Object object : objects){
            setChanged();
            notifyObservers(object);
        //}

    }
}
