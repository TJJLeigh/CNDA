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
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

/**
 * Created by Jack on 2016-01-09.
 */
public class PongClient extends GameState implements InputProcessor{
    String message = "";
    String messageSend = "";
    Client client;
    Boolean inputBeingTyped = false;
    public String hostIP;
    ShapeRenderer shapeRenderer;
    Paddle pad1;
    Paddle pad2;
    Ball ball;
    BitmapFont font;
    SpriteBatch batch;
    public float score1, score2;
    public PongClient(GameStateManager gsm){
        super(gsm);
        init(new String[0]);
    }
    @Override
    public void init(String[] args) {
        Gdx.input.setInputProcessor(this);
        hostIP = gsm.hostIP;
        font = new BitmapFont();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        pad1 = new Paddle(80,300);
        pad2 = new Paddle(720,300);
        ball = new Ball(400,300);
        client = new Client();
        new Thread(client).start();
        /*
        InetAddress address = client.discoverHost(54777, 5000);
        if (address != null) {
            System.out.println("a");
            hostIP = address.toString();
            System.out.println(hostIP);
        }
        try{
            client.connect(5000,hostIP,54555,54777);
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {*/
            try{
                client.connect(5000,hostIP,54555,54777);
                //client.connect(5000,"127.0.0.1",54555,54777);
            }catch (IOException e){
                e.printStackTrace();
            }
       // }
        // Register classes for serialization
        Kryo kryo = client.getKryo();
        kryo.register(Vector2.class);
        kryo.register(PositionData.class);
        kryo.register(KeyPress.class);
        kryo.register(KeyRelease.class);
        kryo.register(ShittyChatMessage.class);
        kryo.register(ScoreData.class);
        kryo.register(ConfirmResponse.class);
        // Shitty Handshake
        client.sendUDP(new ConfirmResponse());
        //Start a msg polling thread
        //Add a listener to the client that updates the game with position data
        //Received from the server
        client.addListener(new Listener(){
            public void received(Connection connection, Object object){
                if (object instanceof PositionData){
                    PositionData pdata = (PositionData)object;
                    updatePositionData(pdata.paddle1, pdata.paddle2, pdata.ball);
                }
                if(object instanceof ShittyChatMessage){
                    ShittyChatMessage scm = (ShittyChatMessage)object;
                    message = scm.msg;
                }
                if(object instanceof ScoreData){
                    ScoreData sd = (ScoreData)object;
                    score1 = sd.score1;
                    score2 = sd.score2;
                }
            }
        });
    }
    @Override
    public void update(float deltatime) {
    }
    @Override
    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        pad1.draw(shapeRenderer);
        pad2.draw(shapeRenderer);
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
    public void updatePositionData(Vector2 pad1_p, Vector2 pad2_p, Vector2 ball_p){

        pad1.x = pad1_p.x;
        pad1.y = pad1_p.y;

        pad2.x = pad2_p.x;
        pad2.y = pad2_p.y;

        ball.x = ball_p.x;
        ball.y = ball_p.y;
    }


    @Override
    public boolean keyDown(int keycode) {
        if(inputBeingTyped){
            if(keycode == Input.Keys.ENTER){
                client.sendUDP(new ShittyChatMessage(messageSend));
                inputBeingTyped = false;
            }
        }else{
            messageSend = "";
            if (keycode == Input.Keys.ENTER) {
                inputBeingTyped = true;
                return false;
            }
            client.sendUDP(new KeyPress(keycode));

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        client.sendUDP(new KeyRelease(keycode));
        return false;
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
