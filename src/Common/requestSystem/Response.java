package Common.requestSystem;

import Common.entities.SpaceMarine;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private final String message;
    private List<SpaceMarine> spaceMarines = null;
    private int countOfSpaceMarines = 0;

    public Response(String message) {
        this.message = message;
    }

    public Response(List<SpaceMarine> spaceMarines, String message) {
        this.spaceMarines = spaceMarines;
        this.message = message;
        this.countOfSpaceMarines = spaceMarines.size();
    }

    public String getMessage() {
        return message;
    }

    public List<SpaceMarine> getSpaceMarines() {
        return spaceMarines;
    }

    public int getCountOfSpaceMarines() {
        return countOfSpaceMarines;
    }
}
