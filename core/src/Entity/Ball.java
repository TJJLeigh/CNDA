package Entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Albert on 2016-01-09.
 */
public class Ball {
    ShapeRenderer shapeRenderer;
    private final int WIDTH = 50;
    private final int HEIGHT = 50;
    public int x;
    public int y;

    public Ball(){
        shapeRenderer = new ShapeRenderer();

    }

    public void init(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x,y,WIDTH,HEIGHT);
        shapeRenderer.end();

    }
    public void update(){

    }
}
