package server.commands;

import server.managers.CollectionManager;
import lib.managers.OutputManager;

public class History extends Command {
    private static String name;
    private static String description;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    public History(String name, String description, CollectionManager collectionManager, OutputManager outputManager) {
        super("history", "вывести последние 14 команд");this.name = name;
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
            outputManager.print("Неправильное количество аргументов ");
            return false;
        } outputManager.println(collectionManager.getHistory().toString());
        return true;
    }
}
