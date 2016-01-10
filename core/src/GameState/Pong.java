package GameState;

import Entity.Ball;
import Entity.Paddle;
import Network.ConfirmResponse;
import Network.KeyPress;
import Network.KeyRelease;
import Network.PositionData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by Albert on 2016-01-09.
 * netcode written by Jack
 */
public class Pong extends GameState implements InputProcessor{
    final int PADDLE_SPEED = 200;
    ShapeRenderer shapeRenderer;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    boolean up = false,down = false,w = false,s = false;
    Server server;
    public Pong(GameStateManager gsm){
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        paddle1 = new Paddle(80,300);
        paddle2 = new Paddle(720,300);
        ball = new Ball(400,300);
        Gdx.input.setInputProcessor(this);
        init(new String[0]);
    }
    public void init(String args[]){
        server = new Server();
        new Thread(server).start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Kryo kryo = server.getKryo();
        kryo.register(KeyPress.class);
        kryo.register(KeyRelease.class);
        kryo.register(PositionData.class);
        kryo.register(ConfirmResponse.class);

        server.addListener(new Listener(){
            public void receive(Connection connection, Object object){
                if(object instanceof KeyPress) {
                    KeyPress kp = (KeyPress)object;
                    keyDown(kp.keycode);
                    connection.sendUDP(new ConfirmResponse());
                }
                if(object instanceof  KeyRelease){
                    KeyRelease kr = (KeyRelease)object;
                    keyUp(kr.keycode);
                    connection.sendUDP(new ConfirmResponse());
                }
            }
        });
    }

    public void update(float deltatime) {
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
        ball.update(deltatime);
        server.sendToAllUDP(new PositionData(paddle1.x, paddle1.y, paddle2.x, paddle2.y, ball.x, ball.y));
    }

    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        paddle1.draw(shapeRenderer);
        paddle2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.UP){
            up = true;
        }
        if(keycode == Input.Keys.DOWN){
            down = true;
        }
        if(keycode == Input.Keys.W){
            w = true;
        }
        if(keycode == Input.Keys.S){
            s = true;
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
