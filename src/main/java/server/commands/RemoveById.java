package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;

public class RemoveById extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private SQLManager sqlManager;
    private UserManager userManager;
    private boolean k;
    public RemoveById(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager, SQLManager sqlManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.name = name;
        this.description=description;
        this.collectionManager = collectionManager;
        this.serverSendingManager = serverSendingManager;
        this.sqlManager = sqlManager;
        this.userManager=userManager;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public String getDescription(){
        return description;
    }
     @Override
    public boolean execute(Message message) throws InvalidInputException, IOException {
        if(userManager.getUserId(message.getUser().getLogin(), userManager.hashPassword(message.getUser().getPassword()))!=0) {
            try {
                for (var spaceMarine : collectionManager.getCollection()) {
                    if ((spaceMarine.getId()) == Long.parseLong(message.getEntity().toString())) {
                        collectionManager.getCollection().remove(spaceMarine);
                        sqlManager.deleteSpaceMarine(Long.parseLong(message.getEntity().toString()));
                        serverSendingManager.sendMessage(new Message(message.getName(), "Элемент удален", message.getAddress()));
                        k = true;
                        return true;
                    }
                }
                if (!k) {
                    serverSendingManager.sendMessage(new Message(message.getName(), "Нет элемента с таким id", message.getAddress()));
                    return false;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
     }
}
