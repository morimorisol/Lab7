package Common.requestSystem.requests;

import java.io.Serializable;

public enum RequestType implements Serializable {
    SIGN_IN,
    SIGN_UP,
    COMMAND_WITHOUT_ARGS,
    COMMAND_WITH_SPACEMARINE,
    COMMAND_WITH_ID,
    COMMAND_WITH_SPACEMARINE_AND_ID,
    DISCONNECT
}
