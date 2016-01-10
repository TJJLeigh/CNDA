package Entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Albert on 2016-01-09.
 */
public class Paddle {
    ShapeRenderer shapeRenderer;
    private final int WIDTH = 50;
    private final int HEIGHT = 200;
    public int x;
    public int y;

    public Paddle(){
        shapeRenderer = new ShapeRenderer();
    }

    public void init(int x,int y){
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
