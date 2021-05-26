package Application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class postIt {
    private double x,y;
    private Rectangle r;


    public postIt(double x, double y, Rectangle r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setColor(Color color){
        r.setFill(color);
    }

    public void draw()  {
        r.setHeight(100);
        r.setWidth(80);
        r.setTranslateX(x);
        r.setTranslateY(y);
    }
}
