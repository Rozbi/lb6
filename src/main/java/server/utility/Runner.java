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
    private InputManager inputManager;
    private ServerReceivingManager serverReceivingManager;
    private final CollectionManager collectionManager;
    private final ServerSendingManager serverSendingManager;
    private final ServerConnector serverConnector;
    private final OutputManager outputManager;



    public Runner(CollectionManager collectionManager, CommandManager commandManager, ServerReceivingManager serverReceivingManager, ServerSendingManager serverSendingManager, ServerConnector serverConnector, InputManager inputManager, OutputManager outputManager) {
        this.serverReceivingManager = serverReceivingManager;
        this.commandManager = commandManager;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.collectionManager = collectionManager;
        this.serverConnector = serverConnector;
        this.serverSendingManager = serverSendingManager;
    }

    /**
     * метод для запуска интерактивного режима
     **/
    @Override
    public void letsGo() throws InvalidInputException, IOException {
        Thread  thread= new Thread(() -> {
            String input = null;
            try {
                input = inputManager.read();
            } catch (InvalidInputException e) {
                outputManager.printerr("Ошибка ввода");
            }
            if (input.equals("save")) {
                try {
                    collectionManager.history("save");
                    commandManager.getCommandMap().get("save").execute(new Message("save", ""));
                } catch (IOException e) {
                    outputManager.printerr("Ошибка сохранения");
                } catch (InvalidInputException e) {
                    outputManager.printerr("Неправильный ввод\n");
                }
                outputManager.print("Коллекция успешно сохранена\n");
                } else{
                    outputManager.printerr("Такой команды не существует\n");
                }
        });
        thread.start();

        commandManager.addCommands();
        serverConnector.connect();

        while (true) {
            try {

                Message clientMessage = serverReceivingManager.receive();
                String commandName = clientMessage.getName();
                Command command = commandManager.getCommandMap().get(commandName);

                collectionManager.history(command.getName());
                command.execute(clientMessage);

            } catch (IOException e) {
                Message serverMessage = new Message("Error, ошибка выполнения сервером", "Код:1");
                serverSendingManager.sendMessage(serverMessage);
            }
        }
    }

}

