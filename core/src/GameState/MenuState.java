package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



/**
 * Created by Albert on 2016-01-09.
 */
public class MenuState extends GameState implements InputProcessor{
    ShapeRenderer shapeRenderer;
    private int currentChoice = 0;
    private String[] options = {
            "Connect",
            "Host",
            "Quit"
    };
    public MenuState(GameStateManager gsm) {
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);

    }
    public void init(String args[]){

    }
    public void update() {
        inputHandler();

    }
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(currentChoice == 0){
            shapeRenderer.circle(400,300,50);
        }
        else if(currentChoice == 1){
            shapeRenderer.circle(400,200,50);
        }
        else if(currentChoice == 2){
            shapeRenderer.circle(400,100,50);
        }
        shapeRenderer.end();

    }
    public void inputHandler(){
    }

    private void select() {
        if(currentChoice == 0) {
            gsm.setState(GameStateManager.PONG);
        }
        else if (currentChoice == 1) {
            gsm.setState(GameStateManager.PONGCLIENT);
        }
        else if (currentChoice == 2) {
            System.exit(0);
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            select();
        }
        else if (keycode == Input.Keys.UP) {
            currentChoice--;
            if (currentChoice<0){
                currentChoice = 0;
            }
        }
        else if (keycode == Input.Keys.DOWN) {
            currentChoice++;
            if (currentChoice>(options.length -1)){
                currentChoice = options.length-1;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
