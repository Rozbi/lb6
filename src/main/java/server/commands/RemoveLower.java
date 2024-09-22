package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.spaceMarine.SpaceMarine;
import client.utility.Ask;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.concurrent.*;

public class RemoveLower extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private InputManager inputManager;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    private Ask ask;
    private SQLManager sqlManager;

    public RemoveLower(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager, SQLManager sqlManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
        this.serverSendingManager = serverSendingManager;
        this.userManager = userManager;
        this.sqlManager = sqlManager;
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
                PriorityBlockingQueue<SpaceMarine> collection = new PriorityBlockingQueue<>();
                if (collectionManager.getCollection().isEmpty()) {
                    serverSendingManager.sendMessage(new Message(message.getName(), "коллекция пуста!", message.getAddress()));
                    return false;
                }
                    for (var spaceMarine : collectionManager.getCollection()) {
                        if ((spaceMarine.getId()) < Long.parseLong(message.getEntity().toString())) {
                            sqlManager.deleteSpaceMarine(spaceMarine.getId(), message.getUser());
                            serverSendingManager.sendMessage(new Message(message.getName(), "Элементы удалены", message.getAddress()));
                            collection.addAll(sqlManager.select());
                            collectionManager.setCollection(collection);
                            return true;
                        } else {
                            serverSendingManager.sendMessage(new Message(message.getName(), "все id больше заданного", message.getAddress()));
                            return false;
                        }
                    }
                serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
                return false;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
