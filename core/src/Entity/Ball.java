package Entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Albert on 2016-01-09.
 */
public class Ball {
    ShapeRenderer shapeRenderer;
    private final int RADIUS = 15;
    public float x;
    public float y;
    public float xv = 100;
    public float yv = 100;

    public Ball(int x, int y){
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(x,y,RADIUS);
    }
    public void update(float deltatime, Rectangle r1, Rectangle r2){
        x = x + xv * deltatime;
        y = y + yv * deltatime;
        if(x < 0){
            xv = 100;
        }
        if(x > 800){
            xv = -100;
        }
        if(y > 600){
            yv = -100;
        }
        if(y < 0){
            yv = 100;
        }
    }
}
