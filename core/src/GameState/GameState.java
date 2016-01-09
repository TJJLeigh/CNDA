package GameState;

/**
 * Created by Albert on 2016-01-09.
 */
public abstract class GameState {

    protected GameStateManager gsm;


    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }
    public abstract void init();
    public abstract void update();
    public abstract void draw();

}
