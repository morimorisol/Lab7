package Common.commands;

import Common.entities.CollectionManager;
import Common.requestSystem.Response;

public class RemoveById extends CommandAbstract {

    private final int id;

    public RemoveById(int id) {
        super("remove_by_id", "удалить корабль с текущим значением id", 1);
        this.id = id;
    }

    @Override
    public Response execute(CollectionManager manager) {
        return new Response(manager.removeById(id));
    }
}
