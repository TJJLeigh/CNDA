package GameState;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Albert on 2016-01-09.
 */
public class Pong extends GameState{
    ShapeRenderer shapeRenderer;
    public Pong(GameStateManager gsm){
        super(gsm);
        shapeRenderer = new ShapeRenderer();
    }
    public void init(){}

    public void update() {

    }
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(50,50,300,300);
        shapeRenderer.end();

    }

}
