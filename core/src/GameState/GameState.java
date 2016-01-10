package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Albert on 2016-01-09.
 */
public abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }
    public abstract void init(String args[]);
    public abstract void update(float deltatime);
    public abstract void draw();

}
