package Network;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jack on 2016-01-09.
 */
public class PositionData {
    public PositionData(float p1x, float p1y, float p2x, float p2y, float ballx, float bally){
        this.paddle1 = new Vector2(p1x, p1y);
        this.paddle2 = new Vector2(p2x, p2y);
        this.ball = new Vector2(ballx, bally);
    }
    public Vector2 paddle1;
    public Vector2 paddle2;
    public Vector2 ball;
}
