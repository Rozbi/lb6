package server.commands;


import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;

public class Exit extends Command {
    private static String name;
    private static String description;
    private ServerSendingManager serverSendingManager;
    private SQLManager sqlManager;
    private CollectionManager collectionManager;
    private UserManager userManager;

    public Exit(String name, String description, ServerSendingManager serverSendingManager, CollectionManager collectionManager, SQLManager sqlManager, UserManager userManager) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.name = name;
        this.description = description;
        this.serverSendingManager = serverSendingManager;
        this.sqlManager = sqlManager;
        this.collectionManager = collectionManager;
        this.userManager=userManager;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean execute(Message message) throws InvalidInputException, IOException {
        if (userManager.getUserId(message.getUser().getLogin(), userManager.hashPassword(message.getUser().getPassword())) != 0) {
            try {
                sqlManager.createSpaceMarine(collectionManager.getCollection(), message.getUser());
                serverSendingManager.sendMessage(new Message("exit", "Завершение выполнения программы...", message.getAddress()));
                return true;
            } catch (Exception e) {
                return false;
            }
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}