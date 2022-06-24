package Common.entities;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class Coordinates implements Serializable {

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    private int x;

    private float y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}
