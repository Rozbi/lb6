package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.spaceMarine.SpaceMarine;
import client.utility.Ask;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class RemoveLower extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private InputManager inputManager;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    private Ask ask;

    public RemoveLower(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.name = name;
        this.description = description;
        this.ask = ask;
        this.collectionManager = collectionManager;
        this.serverSendingManager = serverSendingManager;
        this.inputManager = inputManager;
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
        if(userManager.getUserId(message.getUser().getLogin(), message.getUser().getPassword())!=0) {
            try {
                if (collectionManager.getCollection().isEmpty()) {
                    serverSendingManager.sendMessage(new Message(message.getName(), "коллекция пуста!", message.getAddress()));
                    return false;
                } else {
                    PriorityQueue<SpaceMarine> queue = collectionManager.getCollection().stream()
                            .filter(spaceMarine -> spaceMarine.getId() > Long.parseLong(message.getEntity().toString()))
                            .collect(Collectors.toCollection(PriorityQueue::new));
                    collectionManager.setCollection(queue);
                    serverSendingManager.sendMessage(new Message(message.getName(), "Элементы удалены", message.getAddress()));
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}
