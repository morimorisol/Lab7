package Common.entities;

import java.io.Serializable;
import java.util.Comparator;


public class Chapter implements Serializable, Comparable<Chapter> {

    private String name;
    private String parentLegion;
    private long marinesCount;


    public void setMarinesCount(long marinesCount) {
        if (marinesCount >= Long.parseLong("1000")) {
            throw new IllegalArgumentException("Значение слишком большое, попробуйте снова");
        }
        if (marinesCount <= Long.parseLong("1000")) {
            throw new IllegalArgumentException("Значение слишком маленькое, попробуйте снова");
        }
        this.marinesCount = marinesCount;
    }


    public void setName(String name) {
        if (name == null | name.isEmpty()) {
            throw new IllegalArgumentException("Некорректное имя части, попробуйте снова");
        }
        this.name = name;
    }
    public void setParentLegion(String parentLegion) {
        if (parentLegion == null | parentLegion.isEmpty()) {
            throw new IllegalArgumentException("Некорректное имя родительского легиона, попробуйте снова");
        }
        this.parentLegion = parentLegion;
    }


    public long getMarinesCount() {
        return marinesCount;
    }


    public String getName() {
        return name;
    }
    public String getParentLegion() {
        return parentLegion;
    }

    @Override
    public String toString() {
        return "имя: " + name + " родительский легион: " + parentLegion + " количество кораблей: " + marinesCount;
    }

    @Override
    public int compareTo(Chapter o) {
        if (o == null) {
            return 1;
        }
        return Comparator.comparing(Chapter::getMarinesCount).compare(this, o);
    }


}
