package Common.commands;

import Common.entities.CollectionManager;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class ExitCommand extends CommandAbstract {

    public ExitCommand() {
        super("exit", "Выход из программы с сохранением", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        return new Response(TextFormatter.colorInfoMessage("Connection disabled"));
    }
}
