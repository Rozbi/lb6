package server.utility;
import lib.utility.Message;
import lib.utility.Runnable;
import lib.managers.InputManager;
import server.commands.Command;
import server.exeptions.InvalidInputException;
import server.managers.*;
import lib.managers.OutputManager;
import server.managers.ServerReceivingManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

/**класс для вызова команд*/
public class Runner implements Runnable {
    private CommandManager commandManager;
    private ServerReceivingManager serverReceivingManager;
    private final CollectionManager collectionManager;
    private final ServerSendingManager serverSendingManager;
    private final ServerConnector serverConnector;


    public Runner(CollectionManager collectionManager, CommandManager commandManager, ServerReceivingManager serverReceivingManager, ServerSendingManager serverSendingManager, ServerConnector serverConnector) {
        this.serverReceivingManager = serverReceivingManager;
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
        this.serverConnector = serverConnector;
        this.serverSendingManager = serverSendingManager;
    }

    /**
     * метод для запуска интерактивного режима
     **/
    @Override
    public void letsGo() throws InvalidInputException, IOException {
        while (true) {
            try {
                commandManager.addCommands();
                serverConnector.connect();
                Message clientMessage = serverReceivingManager.receive();
                String commandName = clientMessage.getName();
                Command command = commandManager.getCommandMap().get(commandName);
                collectionManager.history(command.getName());
                command.execute(clientMessage);
            } catch (IOException | InterruptedException e) {
                Message serverMessage = new Message("Error, ошибка выполнения сервером", "Код:1");
            }
        }
    }

}

