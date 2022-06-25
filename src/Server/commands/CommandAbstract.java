package Server.commands;

import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

import java.io.Serializable;

public abstract class CommandAbstract implements Serializable {

    private final String name;
    private final DatabaseWorker databaseWorker;

    public CommandAbstract(String name, DatabaseWorker databaseWorker) {
        this.name = name;
        this.databaseWorker = databaseWorker;
    }

    public abstract Object execute(CollectionManager manager);

    public String getName() {
        return name;
    }

    public DatabaseWorker getDatabaseWorker() {
        return databaseWorker;
    }
}
