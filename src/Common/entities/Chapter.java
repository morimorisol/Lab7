package Common.entities;

import java.io.Serializable;
import java.util.Comparator;


public class Chapter implements Serializable, Comparable<Chapter> {

    private String name;
    private long marinesCount;

    public Chapter(String name, long marinesCount) {
        this.name = name;
        this.marinesCount = marinesCount;
    }

    public Chapter(){}

    public void setMarinesCount(long marinesCount) {
        if (marinesCount >= Long.parseLong("1000")) {
            throw new IllegalArgumentException("Значение слишком большое, попробуйте снова");
        }
        if (marinesCount < Long.parseLong("0")) {
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
    }


    public long getMarinesCount() {
        return marinesCount;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "имя: " + name + " количество кораблей: " + marinesCount;
    }

    @Override
    public int compareTo(Chapter o) {
        if (o == null) {
            return 1;
        }
        return Comparator.comparing(Chapter::getMarinesCount).compare(this, o);
    }


}
