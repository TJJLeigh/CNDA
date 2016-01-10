package Network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * Created by Jack on 2016-01-10.
 */
public class KryoRegistry {
    public static void RegisterKryo(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(Vector2.class);
        kryo.register(PositionData.class);
        kryo.register(KeyPress.class);
        kryo.register(KeyRelease.class);
        kryo.register(ShittyChatMessage.class);
        kryo.register(ScoreData.class);
        kryo.register(ConfirmResponse.class);

    }

}
