package GameState;


import Entity.Ball;
import Entity.Paddle;
import Network.ConfirmResponse;
import Network.KeyPress;
import Network.KeyRelease;
import Network.PositionData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
    Client client;
    ShapeRenderer shapeRenderer;
    Paddle pad1;
    Paddle pad2;
    Ball ball;
    public PongClient(GameStateManager gsm){
        super(gsm);
        init(new String[0]);
    }
    @Override
    public void init(String[] args) {
        Gdx.input.setInputProcessor(this);
        shapeRenderer = new ShapeRenderer();
        pad1 = new Paddle(80,300);
        pad2 = new Paddle(720,300);
        ball = new Ball(400,300);
        client = new Client();
        new Thread(client).start();
        try{
            client.connect(5000,"142.1.84.31",54555,54777);
        }catch (IOException e){
            e.printStackTrace();
        }
        Kryo kryo = client.getKryo();
        kryo.register(Vector2.class);
        kryo.register(PositionData.class);
        kryo.register(KeyPress.class);
        kryo.register(KeyRelease.class);
        kryo.register(ConfirmResponse.class);
        client.sendUDP(new ConfirmResponse());
        client.addListener(new Listener(){
            public void received(Connection connection, Object object){
                if (object instanceof PositionData){
                    PositionData pdata = (PositionData)object;
                    updatePositionData(pdata.paddle1, pdata.paddle2, pdata.ball);
                    connection.sendUDP(new ConfirmResponse());
                }
            }
        });
    }
    @Override
    public void update(float deltatime) {
        client.sendUDP(new ConfirmResponse());
    }
    @Override
    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        pad1.draw(shapeRenderer);
        pad2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();
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
        client.sendUDP(new KeyPress(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        client.sendUDP(new KeyRelease(keycode));
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
