package Client.commandDispatcher;

import Common.commands.CommandAbstract;
import Common.requestSystem.Serializer;


import java.io.IOException;
import java.nio.ByteBuffer;

public class CommandBuilder {

    private CommandBuilder() {
        //never used
    }

    public static ByteBuffer buildCommand(String input) throws IOException {
        CommandAbstract command = CommandHandler.initCommand(input);
        if (command == null) {
            throw new NullPointerException("Command is incorrect. Try again");
        }
        return Serializer.serializeCommand(command);
    }

}
