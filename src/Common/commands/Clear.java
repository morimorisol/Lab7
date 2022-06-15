package Common.commands;


import Common.entities.CollectionManager;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class Clear extends CommandAbstract {

    public Clear() {
        super("clear", "Очищение коллекции", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        manager.clear();
        return new Response(TextFormatter.colorInfoMessage("Collection successfully cleared"));
    }
}
