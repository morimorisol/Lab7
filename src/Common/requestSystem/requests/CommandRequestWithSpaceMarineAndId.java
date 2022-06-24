package Common.requestSystem.requests;

import Common.entities.SpaceMarine;
import javafx.util.Pair;

import java.io.Serializable;

public class CommandRequestWithSpaceMarineAndId implements CommandRequest, Serializable {

    private static final RequestType TYPE = RequestType.COMMAND_WITH_SPACEMARINE_AND_ID;
    private final String name;
    private final SpaceMarine spaceMarine;
    private final long id;
    private final Pair<String, String> loginData;

    public CommandRequestWithSpaceMarineAndId(String name, SpaceMarine spaceMarine, long id, Pair<String, String> loginData) {
        this.name = name;
        this.spaceMarine = spaceMarine;
        this.id = id;
        this.loginData = loginData;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Pair<String, String> getPair() {
        return loginData;
    }

    @Override
    public RequestType getType() {
        return TYPE;
    }
}
