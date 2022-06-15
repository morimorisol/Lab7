package Common.commands;


import Common.entities.CollectionManager;
import Common.entities.Dragon;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class AddCommand extends CommandAbstract {

    private Dragon dragon;

    public AddCommand(Dragon dragon) {
        super("add", "Добавить элемент в коллекцию", Dragon.COUNT_OF_PRIMITIVE_ARGS);
        this.dragon = dragon;
    }

    @Override
    public Response execute(CollectionManager manager) {
        manager.addDragon(dragon);
        return new Response(TextFormatter.colorInfoMessage("Dragon successfully added"));
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }
}
