package Entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Albert on 2016-01-09.
 */
public class Ball {

    private final int RADIUS = 15;
    public float x;
    public float y;
    public float xv = 200;
    public float yv = 200;
    public Rectangle ballRect;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        ballRect = new Rectangle(this.x, this.y, 30, 30);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(x, y, RADIUS);
    }

    public void update(float deltatime, Rectangle r1, Rectangle r2) {
        x = x + xv * deltatime;
        y = y + yv * deltatime;
        if (x < 0 || x > 800) {
            xv *= -1;
        }
        if (y > 600 || y < 0) {
            yv *= -1;
        }

        ballRect.x = x - 15;
        ballRect.y = y - 15;

        if (ballRect.overlaps(r1) || ballRect.overlaps(r2)) {
            xv *= -1;
        }
    }
}
