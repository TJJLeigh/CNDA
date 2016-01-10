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
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Albert on 2016-01-09.
 * netcode written by probably also Albert
 * and definitely not jack
 * unless it works
 */
public class Pong extends GameState implements InputProcessor{
    final int PADDLE_SPEED = 300;
    String messageSend = "";
    String message = "";
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    SpriteBatch batch;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    public float score1;
    public float score2;
    boolean up = false,down = false,w = false,s = false;
    boolean inputBeingTyped = false;
    Server server;
    ArrayList<Connection> clients;
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
        clients = new ArrayList<Connection>();
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
        kryo.register(Vector2.class);
        kryo.register(PositionData.class);
        kryo.register(KeyPress.class);
        kryo.register(KeyRelease.class);
        kryo.register(ShittyChatMessage.class);
        kryo.register(ScoreData.class);
        kryo.register(ConfirmResponse.class);
        server.addListener(new Listener(){
            public void received(Connection connection, Object object){
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
                if(object instanceof ConfirmResponse){
                    clients.add(connection);
                }
                if(object instanceof ShittyChatMessage){
                    ShittyChatMessage scm = (ShittyChatMessage)object;
                    message = scm.msg;
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
        scoreUpdate();
        ball.update(deltatime,
                new Rectangle(paddle1.x, paddle1.y, 20, 100),
                new Rectangle(paddle2.x, paddle2.y, 20, 100));
        /*for(Connection c: clients){
            c.sendUDP(new PositionData(paddle1.x, paddle1.y, paddle2.x,paddle2.y,ball.x,ball.y));
        }*/
        server.sendToAllUDP(new PositionData(paddle1.x, paddle1.y, paddle2.x,paddle2.y,ball.x,ball.y));
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
            font.draw(batch, "/:" + messageSend, 250,100);
        }
        font.draw(batch,"" + message,100,100);
        font.draw(batch,"" + messageSend,100,100 + 20);

        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (inputBeingTyped){
            if(keycode == Input.Keys.ENTER){
                server.sendToAllUDP(new ShittyChatMessage(messageSend));
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
            server.sendToAllUDP(new ScoreData(score1,score2));
            ball.x = 400;
            ball.y = 300;
        }
        else if (ball.x >= 800){
            score2+= 1;
            server.sendToAllUDP(new ScoreData(score1,score2));
            ball.x = 400;
            ball.y = 300;

        }
    }

    @Override
    public boolean keyTyped(char character) {
        if(inputBeingTyped){
            messageSend = messageSend + character;
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
