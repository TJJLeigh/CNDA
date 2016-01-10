package Network;
import GameState.Pong;
import GameState.GameStateInformation;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jack on 2016-01-10.
 */
public class PongServer implements Observer{
    // GameStateInformation object
    GameStateInformation gameStateInformation;
    // Server object
    Server server;
    // Receives messages from the gamestate and updates the clients
    @Override
    public void update(Observable obs, Object object){
        if(     object instanceof PositionData ||
                object instanceof ScoreData ||
                object instanceof ShittyChatMessage) {
            server.sendToAllUDP(object);
        }
    }

    public PongServer(Pong observerState){
        // Create observable game state information so gamestate
        // can receive information from the server as well
        gameStateInformation = new GameStateInformation();
        gameStateInformation.addObserver(observerState);
        // Create server
        server = new Server();
        // Try to bind the server with UDP 54777, and TCP 54555
        try {
            server.bind(54555, 54777);
        }catch (IOException e){
            e.printStackTrace();
        }
        // Start the server Thread
        new Thread(server).start();
        // Register the server communication objects with Kryo for
        // serialization
        KryoRegistry.RegisterKryo(server);

        server.addListener(new Listener(){
            public void received(Connection connection, Object object){
                if(object instanceof KeyPress || object instanceof  KeyRelease) {
                    gameStateInformation.updateGameState(object);
                }
                if(object instanceof ShittyChatMessage){
                    ShittyChatMessage scm = (ShittyChatMessage)object;
                    System.out.println(scm.msg);
                }
            }
        });    }
}
