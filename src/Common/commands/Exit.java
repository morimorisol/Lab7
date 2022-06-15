package Common.commands;

import Common.entities.CollectionManager;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class Exit extends CommandAbstract {

    public Exit() {
        super("exit", "Выход из программы с сохранением", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        return new Response(TextFormatter.colorInfoMessage("Connection disabled"));
    }
}
