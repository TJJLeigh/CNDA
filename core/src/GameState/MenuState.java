package GameState;

import Entity.Ball;
import Entity.Paddle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Albert on 2016-01-09.
 */
public class MenuState extends GameState implements InputProcessor{
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    SpriteBatch batch;
    Ball ball;
    Paddle p1;
    Paddle p2;
    public String address = "";
    private int currentChoice = 0;
    private String[] options = {
            "Host",
            "Connect",
            "Quit"
    };
    public MenuState(GameStateManager gsm) {
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();
        p1 = new Paddle(80,300);
        p2 = new Paddle(720,300);
        ball = new Ball(400,300);
        ball.xv = 0;
        ball.yv = 0;
        Gdx.input.setInputProcessor(this);

    }
    public void init(String args[]){

    }
    public void tick(float deltatime) {
        move();
        ball.update(deltatime,
                new Rectangle(p1.x, p1.y, 20, 100),
                new Rectangle(p2.x, p2.y, 20, 100));
    }
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        p1.draw(shapeRenderer);
        p2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        if(currentChoice == 0){
            ball.x = 425;
            ball.y = 295;
        }
        else if(currentChoice == 1){
            ball.x = 425;
            ball.y = 250;
        }
        else if(currentChoice == 2){
            ball.x = 425;
            ball.y = 200;
        }
        shapeRenderer.end();
        batch.begin();
        font.draw(batch,options[0],365,300);
        font.draw(batch,options[1],350,250);
        font.draw(batch,options[2],365,200);
        if(currentChoice == 1){
            font.draw(batch,"IP Address: ",450,300 );
            font.draw(batch,address,550,300);
        }
        batch.end();

    }

    private void select() {
        if(currentChoice == 0) {
            gsm.setState(GameStateManager.PONG);
        }
        else if (currentChoice == 1) {
            gsm.hostIP = address;
            gsm.setState(GameStateManager.PONGCLIENT);
        }
        else if (currentChoice == 2) {
            System.exit(0);
        }
    }

    public void move(){
        if(p1.y + 50 < ball.y){
            p1.y+=2.0;
        }else if(p1.y + 50 > ball.y){
            p1.y-=2.0;
        }if(p2.y + 50 < ball.y){
            p2.y+=2.0;
        }else if(p2.y + 50 > ball.y){
            p2.y-=2.0;
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
        if(Character.isDigit(character) || character == '.') {
            address = address + character;
        }
        if(character == '' && address.length() > 0) {
            address = address.substring(0, address.length() - 1);
        }
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
