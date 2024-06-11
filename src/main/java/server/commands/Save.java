package server.commands;

import lib.utility.Message;
import server.managers.CollectionManager;

import java.io.IOException;

public class Save extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;

    public Save(String name, String description, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
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
    public boolean execute(Message message) {
        try {
            collectionManager.save();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
