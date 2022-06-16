package Common.commands;


import Common.entities.CollectionManager;
import Common.handlers.HistorySaver;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class History extends CommandAbstract {

    private static final int COUNT_OF_WATCHABLE_COMMANDS = 9;

    public History() {
        super("history", "Вывести последние 9 команд (без их аргументов)", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        System.out.println(HistorySaver.getCommandsHistory().size());
        if (HistorySaver.getCommandsHistory().size() > COUNT_OF_WATCHABLE_COMMANDS) {
            return new Response(TextFormatter.colorMessage(HistorySaver.getCommandsHistory().subList(HistorySaver.getCommandsHistory().size()
                    - COUNT_OF_WATCHABLE_COMMANDS, HistorySaver.getCommandsHistory().size()).toString()));
        }
        return new Response(TextFormatter.colorMessage(HistorySaver.getCommandsHistory().toString()));
    }
}
