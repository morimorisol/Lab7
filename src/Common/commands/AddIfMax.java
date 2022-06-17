package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class AddIfMax extends CommandAbstract {

    private final SpaceMarine spaceMarine;

    public AddIfMax(SpaceMarine spaceMarine) {
        super("add_if_max", "Добавить корабль в коллекцию, если его показатель здоровья выше всех существующих", SpaceMarine.COUNT_OF_PRIMITIVE_ARGS);
        this.spaceMarine = spaceMarine;
    }

    @Override
    public Response execute(CollectionManager manager) {
        long maxHealth = manager.getMax().getHealth();
        if (spaceMarine.getHealth() > maxHealth) {
            manager.addSpaceMarine(spaceMarine);
            return new Response(TextFormatter.colorInfoMessage("Корабль успешно добавлен"));
        } else {
            return new Response(TextFormatter.colorInfoMessage("В коллекции есть корабль получше!"));
        }
    }
}
