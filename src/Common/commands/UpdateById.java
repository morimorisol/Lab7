package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class UpdateById extends CommandAbstract {

    private long id;
    private SpaceMarine dragon;

    public UpdateById(long id, SpaceMarine dragon) {
        super("update_by_id", "Обновить данные о элементе коллекции по данному id", 1);
        this.dragon = dragon;
        this.id = id;
    }

    @Override
    public Response execute(CollectionManager manager) {
        boolean flag = false;
        for (SpaceMarine elem : manager.getDragons()) {
            if (elem.getId() == id) {
                manager.removeById(id);
                flag = true;
            }
        }
        if (flag) {
            manager.addDragon(dragon);
            return new Response(TextFormatter.colorInfoMessage("Info about dragon successfully updated"));
        } else {
            return new Response(TextFormatter.colorInfoMessage("ID not found"));
        }
    }
}
