package Common.commands;

import Common.entities.CollectionManager;
import Common.requestSystem.Response;

public class Info extends CommandAbstract {

    public Info() {
        super("info", "Вывести информацию о коллекции", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        return new Response(manager.showInfo());
    }
}
