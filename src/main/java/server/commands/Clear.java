package server.commands;

import server.managers.CollectionManager;
import lib.managers.OutputManager;

public class Clear extends Command {
    private static String name;
    private static String description;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    public Clear(String name, String description, OutputManager outputManager, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");this.name = name;
        this.description=description;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
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
    public boolean execute(String arg){
        if (!arg.isEmpty()){
            outputManager.println("Неправильное количество аргументов ");
            return false;
        } collectionManager.clear();
        outputManager.println("Коллекция очищена ");
        return true;
    }
}
