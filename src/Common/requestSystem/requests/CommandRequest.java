package Common.requestSystem.requests;

import javafx.util.Pair;

import java.io.Serializable;

public interface CommandRequest extends Request, Serializable {
    String getName();
    Pair<String, String> getPair();
}
