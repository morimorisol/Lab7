package Client.commandDispatcher;

import Common.requestSystem.Serializer;
import Server.commands.CommandAbstract;


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
