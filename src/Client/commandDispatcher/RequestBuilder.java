package Client.commandDispatcher;

import Common.requestSystem.Serializer;
import Common.requestSystem.requests.CommandRequest;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class RequestBuilder {

    private RequestBuilder() {
        //never used
    }

    public static ByteBuffer buildRequest(String input, Pair<String, String> loginData) throws Exception {
        CommandRequest request = initCommand(input, loginData);
        if (request == null) {
            throw new NullPointerException("Command is incorrect. Try again");
        }
        Serializer serializer = new Serializer();
        return serializer.serializeRequest(request);
    }

    private static CommandRequest initCommand(String line, Pair<String, String> loginData) throws Exception {
        List<String> commandWithArgs = LineSplitter.smartSplit(line);
        String commandName = getCommandName(commandWithArgs);
        List<String> commandArgs = getCommandArguments(commandWithArgs);
        CommandFactory factory = new CommandFactory(loginData);
        return factory.createCommand(commandName, commandArgs);
    }

    /**
     * Метод, извлекающий из полученного массива аргументов данные, которые являются аргументами
     *
     * @param line разделенная строка
     * @return массив аргументов
     */
    private static List<String> getCommandArguments(List<String> line) {
        line.remove(0);
        return line;
    }

    /**
     * Метод, извлекающий из полученного массива строк имя команды
     *
     * @param line разделенная строка
     * @return имя команды
     */
    private static String getCommandName(List<String> line) {
        return line.get(0);
    }
}
