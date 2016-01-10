package Entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Albert on 2016-01-09.
 */
public class Paddle {
    ShapeRenderer shapeRenderer;
    private final int WIDTH = 20;
    private final int HEIGHT = 100;
    public int x;
    public int y;

    public Paddle(int x,int y){
        this.x = x;
        this.y = y;
        shapeRenderer = new ShapeRenderer();
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.rect(x,y,WIDTH,HEIGHT);

    }
    public void update(){

    }

    private void move(int x,int y){

    }
}
