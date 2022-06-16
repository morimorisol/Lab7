package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class AddIfMin extends CommandAbstract {

    private final SpaceMarine spaceMarine;

    public AddIfMin(SpaceMarine spaceMarine) {
        super("add_if_min", "Добавить корабль в коллекцию, если его показатель здоровья меньше остальных", SpaceMarine.COUNT_OF_PRIMITIVE_ARGS);
        this.spaceMarine = spaceMarine;
    }

    @Override
    public Response execute(CollectionManager manager) {
        long minHealth = manager.getMin().getHealth();
        if (spaceMarine.getHealth() < minHealth) {
            manager.addSpaceMarines(spaceMarine);
            return new Response(TextFormatter.colorInfoMessage("Корабль успешно добавлен"));
        } else {
            return new Response(TextFormatter.colorInfoMessage("В коллекции есть корабль похуже!"));
        }
    }
}
