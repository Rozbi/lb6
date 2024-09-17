package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class Save extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private SQLManager sqlManager;
    private UserManager userManager;
    private ServerSendingManager serverSendingManager;
    public Save(String name, String description, CollectionManager collectionManager, SQLManager sqlManager, UserManager userManager, ServerSendingManager serverSendingManager) {
        super("save", "сохранить коллекцию в файл");
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
        this.sqlManager = sqlManager;
        this.userManager=userManager;
        this.serverSendingManager = serverSendingManager;
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
            try {
                collectionManager.setLastSaveTime(LocalDateTime.now());
                sqlManager.createSpaceMarine(collectionManager.getCollection(), message.getUser());
                return true;
            } catch (Exception e) {
                return false;
            }
    }
}
