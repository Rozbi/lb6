package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;

public class Info extends Command {
    private CollectionManager collectionManager;
    private static String name;
    private static String description;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    public Info(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");this.name = name;
        this.description=description;
        this.collectionManager = collectionManager;
        this.serverSendingManager = serverSendingManager;
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
        if(userManager.getUserId(message.getUser().getLogin(), message.getUser().getPassword())!=0) {
            try {
                serverSendingManager.sendMessage(new Message("info", collectionManager.getCollection().getClass().toString() + " " + collectionManager.getCollection().size() + " " + collectionManager.getLastInitTime() + " " + collectionManager.getLastSaveTime(), message.getAddress()));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
        return false;
    }
}
