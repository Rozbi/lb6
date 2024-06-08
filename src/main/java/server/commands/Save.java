package server.commands;

import server.managers.CollectionManager;
import lib.managers.OutputManager;

public class Save extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    public Save(String name, String description, CollectionManager collectionManager, OutputManager outputManager) {
        super("save", "сохранить коллекцию в файл");
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
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
    public boolean execute(String arg){
        if (!arg.isEmpty()) {
            outputManager.println("Неправильное количество аргументов!");
            return false;
        }
        collectionManager.save();
        return true;
    }
}
