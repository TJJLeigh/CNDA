package GameState;

import Entity.Ball;
import Entity.Paddle;
import Network.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Albert on 2016-01-09.
 * netcode written by probably also Albert
 * and definitely not jack
 * unless it works
 */
public class Pong extends GameState implements InputProcessor, Observer{
    final int PADDLE_SPEED = 300;
    String messageSend = "";
    String message = "";
    ShapeRenderer shapeRenderer;
    GameStateInformation gameStateInformation;
    BitmapFont font;
    SpriteBatch batch;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    public float score1;
    public float score2;
    boolean up = false,down = false,w = false,s = false;
    boolean inputBeingTyped = false;
    PongServer server;

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof KeyPress){
            KeyPress kp = (KeyPress)arg;
            System.out.println("I'm receiving key presses");
            keyDown(kp.keycode);
        }
        if(arg instanceof  KeyRelease){
            KeyRelease kr = (KeyRelease)arg;
            keyUp(kr.keycode);
        }
        if(arg instanceof ShittyChatMessage){
            ShittyChatMessage scm = (ShittyChatMessage)arg;
            message = scm.msg;
        }
    }
    public Pong(GameStateManager gsm){
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        paddle1 = new Paddle(80,300);
        paddle2 = new Paddle(720,300);
        ball = new Ball(400,300);
        Gdx.input.setInputProcessor(this);
        init(new String[0]);
        server = new PongServer(this);
        gameStateInformation = new GameStateInformation();
        gameStateInformation.addObserver(server);
    }
    public void init(String args[]){
    }

    public void tick(float deltatime) {
        if (up){
            paddle2.y += PADDLE_SPEED * deltatime;
        }
        if (down){
            paddle2.y -= PADDLE_SPEED * deltatime;
        }
        if (w){
            paddle1.y += PADDLE_SPEED * deltatime;
        }
        if (s){
            paddle1.y -= PADDLE_SPEED * deltatime;
        }
        scoreUpdate();
        ball.update(deltatime,
                new Rectangle(paddle1.x, paddle1.y, 20, 100),
                new Rectangle(paddle2.x, paddle2.y, 20, 100));
        gameStateInformation.updateGameState(new PositionData(paddle1.x, paddle1.y,paddle2.x, paddle2.y, ball.x,ball.y));
    }

    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        paddle1.draw(shapeRenderer);
        paddle2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();
        batch.begin();
        //Test
        font.draw(batch,"" + score1, 700, 550);
        font.draw(batch,"" + score2, 100, 550);
        if(inputBeingTyped){
            font.draw(batch, "/: " + messageSend, 250,100);
        }
        font.draw(batch,"Received: " + message,100,100);
        font.draw(batch,"Sent: " + messageSend,100,100 + 20);

        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (inputBeingTyped){
            if(keycode == Input.Keys.ENTER){
                gameStateInformation.updateGameState(new ShittyChatMessage(messageSend));
                inputBeingTyped = false;
            }
        }

        else {
            messageSend = "";
            if (keycode == Input.Keys.ENTER) {
                inputBeingTyped = true;
                return false;
            }
            if (keycode == Input.Keys.UP) {
                up = true;
            }
            if (keycode == Input.Keys.DOWN) {
                down = true;
            }
            if (keycode == Input.Keys.W) {
                w = true;
            }
            if (keycode == Input.Keys.S) {
                s = true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.UP){
            up = false;
        }
        if(keycode == Input.Keys.DOWN){
            down = false;
        }
        if(keycode == Input.Keys.W){
            w = false;
        }
        if(keycode == Input.Keys.S){
            s = false;
        }
        return false;
    }
    public void scoreUpdate(){
        if (ball.x <= 0){
            score1+= 1;
            gameStateInformation.updateGameState(new ScoreData(score1,score2));
            ball.x = 400;
            ball.y = 300;
        }
        else if (ball.x >= 800){
            score2+= 1;
            gameStateInformation.updateGameState(new ScoreData(score1,score2));
            ball.x = 400;
            ball.y = 300;

        }
    }

    @Override
    public boolean keyTyped(char character) {
        if(inputBeingTyped){
            if(character == '' && messageSend.length() > 0) {
                messageSend = messageSend.substring(0, messageSend.length() - 1);
            }else{
                messageSend = messageSend + character;
            }
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
