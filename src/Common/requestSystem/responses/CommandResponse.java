package Common.requestSystem.responses;

import Common.entities.SpaceMarine;

import java.io.Serializable;
import java.util.List;

public class CommandResponse implements Serializable, Response {

    private static final ResponseType type = ResponseType.COMMAND;
    private final String message;
    private List<SpaceMarine> SpaceMarines = null;

    public CommandResponse(String message) {
        this.message = message;
    }

    public CommandResponse(List<SpaceMarine> SpaceMarines, String message) {
        this.SpaceMarines = SpaceMarines;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<SpaceMarine> getSpaceMarines() {
        return SpaceMarines;
    }

    @Override
    public ResponseType getType() {
        return type;
    }
}
