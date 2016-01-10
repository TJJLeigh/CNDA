package GameState;

import Entity.Ball;
import Entity.Paddle;
import Network.PositionData;
import Network.ServerInput;
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
        kryo.register(ServerInput.class);
        kryo.register(PositionData.class);
    }

    public void update() {

    }
    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        paddle1.draw(shapeRenderer);
        paddle2.draw(shapeRenderer);
        ball.draw(shapeRenderer);
        shapeRenderer.end();



    }

}
