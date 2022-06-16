package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class UpdateById extends CommandAbstract {

    private int id;
    private SpaceMarine spaceMarine;

    public UpdateById(int id, SpaceMarine spaceMarine) {
        super("update_by_id", "Обновить данные о элементе коллекции по данному id", 1);
        this.spaceMarine = spaceMarine;
        this.id = id;
    }

    @Override
    public Response execute(CollectionManager manager) {
        boolean flag = false;
        for (SpaceMarine elem : manager.getSpaceMarines()) {
            if (elem.getId() == id) {
                manager.removeById(id);
                flag = true;
            }
        }
        if (flag) {
            manager.addSpaceMarines(spaceMarine);
            return new Response(TextFormatter.colorInfoMessage("Информация о кораблях успешно обновлена"));
        } else {
            return new Response(TextFormatter.colorInfoMessage("ID не найдено"));
        }
    }
}
