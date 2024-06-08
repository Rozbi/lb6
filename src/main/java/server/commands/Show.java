package server.commands;

import lib.managers.OutputManager;
import server.managers.CollectionManager;

public class Show extends Command {
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private static String name;
    private static String description;

    public Show(String name, String description, CollectionManager collectionManager, OutputManager outputManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
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
    public boolean execute(String arg) {
        if (!arg.isEmpty()) {
            outputManager.print("Неправильное количество аргументов ");
            return false;
        } else {
            outputManager.println(collectionManager.getCollection().toString());
        }
        return false;
    }
}
