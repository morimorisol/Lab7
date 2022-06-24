package Common.requestSystem.requests;

import Common.entities.SpaceMarine;
import javafx.util.Pair;

import java.io.Serializable;

public class CommandRequestWithSpaceMarine implements CommandRequest, Serializable {

    private static final RequestType TYPE = RequestType.COMMAND_WITH_SPACEMARINE;
    private final String name;
    private final SpaceMarine SpaceMarine;
    private final Pair<String, String> loginData;

    public CommandRequestWithSpaceMarine(String name, SpaceMarine SpaceMarine, Pair<String, String> loginData) {
        this.name = name;
        this.SpaceMarine = SpaceMarine;
        this.loginData = loginData;
    }

    public SpaceMarine getSpaceMarine() {
        return SpaceMarine;
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
