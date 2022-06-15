package Common.requestSystem;

import Common.entities.SpaceMarine;
import lab.common.util.entities.Dragon;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private final String message;
    private List<SpaceMarine> spaceMarines = null;
    private int countOfDragons = 0;

    public Response(String message) {
        this.message = message;
    }

    public Response(List<SpaceMarine> dragons, String message) {
        this.spaceMarines = dragons;
        this.message = message;
        this.countOfDragons = dragons.size();
    }

    public String getMessage() {
        return message;
    }

    public List<SpaceMarine> getDragons() {
        return spaceMarines;
    }

    public int getCountOfDragons() {
        return countOfDragons;
    }
}
