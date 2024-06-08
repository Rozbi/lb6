package server.commands;

import server.managers.CollectionManager;
import lib.managers.OutputManager;

public class PrintAscending extends Command {
    private static String name;
    private static String description;
    private OutputManager outputManager;
    private CollectionManager collectionManager;

    public PrintAscending(String name, String description, OutputManager outputManager, CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
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
        if (!arg.isEmpty()){
            outputManager.print("Неправильное количество аргументов ");
            return false;
        }
        outputManager.println(String.valueOf(collectionManager.getCollection()));
        return true;
         }
}
