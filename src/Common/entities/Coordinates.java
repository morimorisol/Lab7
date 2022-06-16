package Common.entities;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class Coordinates implements Serializable {

    @NotNull
    private Integer x;

    private float y;

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Integer getX() {
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
