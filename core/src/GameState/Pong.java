package GameState;

import Entity.Ball;
import Entity.Paddle;
import Network.KeyPress;
import Network.KeyRelease;
import Network.PositionData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by Albert on 2016-01-09.
 */
public class Pong extends GameState{
    ShapeRenderer shapeRenderer;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Server server;
    public Pong(GameStateManager gsm){
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        paddle1 = new Paddle(80,300);
        paddle2 = new Paddle(720,300);
        ball = new Ball(400,300);

    }
    public void init(String args[]){
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Kryo kryo = server.getKryo();
        kryo.register(KeyPress.class);
        kryo.register(KeyRelease.class);
        kryo.register(PositionData.class);
    }

    public void update() {

        server.sendToAllUDP(new PositionData(paddle1.x, paddle1.y, paddle2.x, paddle2.y, ball.x, ball.y));
    }
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        paddle1.draw(shapeRenderer);
        paddle2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();



    }

}
