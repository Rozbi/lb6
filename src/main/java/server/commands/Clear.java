package server.commands;

import lib.spaceMarine.SpaceMarine;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

public class Clear extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    private SQLManager sqlManager;
    public Clear(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager,UserManager userManager, SQLManager sqlManager) {
        super("clear", "очистить коллекцию");this.name = name;
        this.description=description;
        this.collectionManager = collectionManager;
        this.serverSendingManager = serverSendingManager;
        this.userManager=userManager;
        this.sqlManager = sqlManager;

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
                sqlManager.clearSpaceMarines(message.getUser());
                PriorityBlockingQueue<SpaceMarine> collection = new PriorityBlockingQueue<>();
                collection.addAll(sqlManager.select());
                collectionManager.setCollection(collection);
                serverSendingManager.sendMessage(new Message(message.getName(), "Коллекция очищена ", message.getAddress()));
                return true;
            } catch (Exception e) {
                return false;
            }
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}
