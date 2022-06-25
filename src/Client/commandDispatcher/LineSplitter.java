package Client.commandDispatcher;

import java.util.ArrayList;
import java.util.List;

public final class LineSplitter {

    private LineSplitter() {}

    public static List<String> smartSplit(String line) {
        List<String> splittedLine = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();
        boolean screeningStarted = false;
        for (char ch : line.toCharArray()) {
            if (ch == ' ' && !screeningStarted) {
                splittedLine.add(currentString.toString());
                currentString.setLength(0);
            } else if (ch == '"') {
                screeningStarted = !screeningStarted;
            } else {
                currentString.append(ch);
            }
        }
        splittedLine.add(currentString.toString());
        return splittedLine;
    }

}
