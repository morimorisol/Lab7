package Common.commands;

import Common.entities.CollectionManager;
import Common.requestSystem.Response;

public class InfoCommand extends CommandAbstract {

    public InfoCommand() {
        super("info", "Вывести информацию о коллекции", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        return new Response(manager.showInfo());
    }
}
