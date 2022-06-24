package Common.requestSystem.responses;

import java.io.Serializable;

public interface Response extends Serializable {
    ResponseType getType();
}
