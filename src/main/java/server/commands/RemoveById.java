package server.commands;

import server.managers.CollectionManager;
import lib.managers.OutputManager;

public class RemoveById extends Command {
    private static String name;
    private static String description;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private boolean k;
    public RemoveById(String name, String description, OutputManager outputManager, CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.name = name;
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
    public boolean execute(String arg) {
         if (arg.isEmpty()) {
             outputManager.print("Неправильное количество аргументов ");
             return false;
         }
         for (var spaceMarine : collectionManager.getCollection()) {
             if ((spaceMarine.getId()) == Long.parseLong(arg)) {
                 collectionManager.getCollection().remove(spaceMarine);
                 outputManager.println("Элемент удален");
                 k = true;
                 return true;
             }
         } if (!k) {
             outputManager.println("Нет элемента с таким id");
             return false;
         }
         return false;
     }
}
