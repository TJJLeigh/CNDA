package GameState;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Albert on 2016-01-09.
 */
public class MenuState extends GameState {
    ShapeRenderer shapeRenderer;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        shapeRenderer = new ShapeRenderer();
    }
    public void init(){

    }
    public void update() {
    }
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(50,50,50,50);
        shapeRenderer.end();
    }
}
