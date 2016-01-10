package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    public void init(String args[]){

    }
    public void update() {
        inputHandler();

    }
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(50,50,50,50);
        shapeRenderer.end();
    }
    public void inputHandler(){
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            select();
        }
    }

    public void select(){
        gsm.setState(1);
    }


}
