package GameState;

/**
 * Created by Albert on 2016-01-09.
 */
public class GameStateManager {

    private GameState[] gameStates;
    private int currentState;
    public static final int NUMGAMESTATES = 3;
    public static final int MENUSTATE = 0;
    public static final int PONG = 1;
    public static final int PONGCLIENT = 2;

    public GameStateManager(){

        gameStates = new GameState[NUMGAMESTATES];
        currentState = MENUSTATE;
        loadState(currentState);
    }

    private void loadState(int state) {
        if(state == MENUSTATE)
            gameStates[state] = new MenuState(this);
        else if(state == PONG)
            gameStates[state] = new Pong(this);
        else if(state == PONGCLIENT)
            gameStates[state] = new PongClient(this);
    }
    public void unloadState(int state) {
        gameStates[state] = null;

    }
    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);

    }
    public void update(float deltatime) {
        if(gameStates[currentState] != null) gameStates[currentState].update(deltatime);
        if(gameStates[currentState] != null) gameStates[currentState].draw();
    }


}
