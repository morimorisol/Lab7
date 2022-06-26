package Server;

import Common.requestSystem.requests.*;
import Common.requestSystem.responses.*;
import Server.commands.*;
import Server.databaseHandlers.AuthorizationModule;
import Server.databaseHandlers.DatabaseWorker;
import Server.databaseHandlers.UsersChecker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RequestHandler {

    private final Connection dbConnection;
    private UsersChecker usersChecker;
    private DatabaseWorker databaseWorker;

    public RequestHandler(Request request, Connection dbConnection) {
        this.dbConnection = dbConnection;
        try {
            this.usersChecker = new UsersChecker(dbConnection, ((CommandRequest) request).getPair());
            this.databaseWorker = new DatabaseWorker(dbConnection, ((CommandRequest) request).getPair());
        } catch (ClassCastException e) {
            this.usersChecker = new UsersChecker(dbConnection, null);
            this.databaseWorker = new DatabaseWorker(dbConnection, null);
        }
    }

    public Response handle(Request request) {
        try {
            if (request.getType().equals(RequestType.COMMAND_WITHOUT_ARGS)) {
                CommandAbstract command = getCommandWithoutArgs((CommandRequestWithoutArgs) request);
                if (!usersChecker.checkUserInData()) {
                    return new CommandResponse("Your login or password is incorrect!");
                }
                return handleCommand(command);
            } else if (request.getType().equals(RequestType.COMMAND_WITH_SPACEMARINE)) {
                CommandAbstract command = getCommandWithSpaceMarine((CommandRequestWithSpaceMarine) request);
                if (!usersChecker.checkUserInData()) {
                    return new CommandResponse("Your login or password is incorrect!");
                }
                return handleCommand(command);
            } else if (request.getType().equals(RequestType.COMMAND_WITH_ID)) {
                CommandAbstract command = getCommandWithId((CommandRequestWithId) request);
                if (!usersChecker.checkUserInData()) {
                    return new CommandResponse("Your login or password is incorrect!");
                }
                return handleCommand(command);
            } else if (request.getType().equals(RequestType.COMMAND_WITH_SPACEMARINE_AND_ID)) {
                CommandAbstract command = getCommandWithSpaceMarineAndId((CommandRequestWithSpaceMarineAndId) request);
                if (!usersChecker.checkUserInData()) {
                    return new CommandResponse("Your login or password is incorrect!");
                }
                return handleCommand(command);
            } else if (request.getType().equals(RequestType.SIGN_IN)) {
                AuthorizationModule authorizationModule =
                        new AuthorizationModule((SignInRequest) request, dbConnection);
                return new SignInResponse(authorizationModule.isCorrectUser(), ((SignInRequest) request).getPair());
            } else if (request.getType().equals(RequestType.SIGN_UP)) {
                AuthorizationModule authorizationModule =
                        new AuthorizationModule((SignUpRequest) request, dbConnection);
                return new SignUpResponse(authorizationModule.isCorrectUser(), ((SignUpRequest) request).getPair());
            }
        } catch (NullPointerException e) {
            ServerConfig.logger.info("Received command is null");
            ServerConfig.logger.info("Response will not write");
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private CommandAbstract getCommandWithoutArgs(CommandRequestWithoutArgs commandRequestWithoutArgs) {
        switch (commandRequestWithoutArgs.getName()) {
            case "clear":
                return new ClearCommand(databaseWorker);
            case "help":
                return new HelpCommand(databaseWorker);
            case "history":
                return new HistoryCommand(databaseWorker);
            case "info":
                return new InfoCommand(databaseWorker);
            case "show":
                return new ShowCommand(databaseWorker);
            case "max_by_chapter":
                return new MaxByChapterCommand(databaseWorker);
            case "print_ascending":
                return new PrintAscendingCommand(databaseWorker);
            case "print_descending":
                return new PrintDescendingCommand(databaseWorker);
            default:
                return null;
        }
    }

    private CommandAbstract getCommandWithSpaceMarine(CommandRequestWithSpaceMarine commandRequestWithSpaceMarine) {
        switch (commandRequestWithSpaceMarine.getName()) {
            case "add":
                return new AddCommand(commandRequestWithSpaceMarine.getSpaceMarine(), databaseWorker);
            case "add_if_min":
                return new AddIfMinCommand(commandRequestWithSpaceMarine.getSpaceMarine(), databaseWorker);
            case "add_if_max":
                return new AddIfMaxCommand(commandRequestWithSpaceMarine.getSpaceMarine(), databaseWorker);
            default:
                return null;
        }
    }

    private CommandAbstract getCommandWithId(CommandRequestWithId commandRequestWithId) {
        switch (commandRequestWithId.getName()) {
            case "remove_by_id":
                return new RemoveByIdCommand(commandRequestWithId.getId(), databaseWorker);
            default:
                return null;
        }
    }

    private CommandAbstract getCommandWithSpaceMarineAndId(CommandRequestWithSpaceMarineAndId commandRequestWithSpaceMarineAndId) {
        switch (commandRequestWithSpaceMarineAndId.getName()) {
            case "update_by_id":
                return new UpdateByIdCommand(commandRequestWithSpaceMarineAndId.getId(),
                        commandRequestWithSpaceMarineAndId.getSpaceMarine(), databaseWorker);
            default:
                return null;
        }
    }

    private CommandResponse handleCommand(CommandAbstract command) {
        ServerConfig.logger.info("Server recieve [" + command.getName() + "] command");
        HistorySaver historySaver = new HistorySaver();
        historySaver.addCommandInHistory(command);
        return (CommandResponse) command.execute(ServerConfig.MANAGER);
    }
}
