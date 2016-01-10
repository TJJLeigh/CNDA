package GameState;

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
    Server server;
    public Pong(GameStateManager gsm){
        super(gsm);
        shapeRenderer = new ShapeRenderer();
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
        shapeRenderer.rect(50,50,300,300);
        shapeRenderer.end();

    }

}
