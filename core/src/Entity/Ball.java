package Entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Albert on 2016-01-09.
 */
public class Ball {
    ShapeRenderer shapeRenderer;
    private final int RADIUS = 15;
    public int x;
    public int y;

    public Ball(int x, int y){
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(x,y,RADIUS);

    }
    public void update(){

    }
}
