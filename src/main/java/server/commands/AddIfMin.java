package server.commands;

import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.spaceMarine.SpaceMarine;
import lib.utility.Ask;
import server.managers.ServerReceivingManager;

public class AddIfMin extends Command {
    private static String name;
    private static String description;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private InputManager inputManager;
    private ServerReceivingManager serverReceivingManager;
    private Ask ask;
    public AddIfMin(String name, String description, Ask ask, ServerReceivingManager serverReceivingManager, OutputManager outputManager, CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");this.name = name;
        this.description=description;
        this.name = name;
        this.ask=ask;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
        this.serverReceivingManager = serverReceivingManager;
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
         if (!arg.isEmpty()) {
             outputManager.print("Неправильное количество аргументов");
             return false;
         }
         SpaceMarine element = ask.getSpaceMarine(outputManager, collectionManager, inputManager);
         if (element != null && element.validate()) {
             Long minElement = 1000L;
             if (collectionManager.getCollection().isEmpty()) {
                 outputManager.print("Коллекция пуста. Элемент не добавлен в коллекцию ");
                 return false;
             }
             for (var spaceMarine : collectionManager.getCollection()) {
                 if (minElement > spaceMarine.getHealth()) {
                     minElement = spaceMarine.getHealth();
                 }
             }
             if (minElement > element.getHealth()) {
                     collectionManager.add(element);
                     outputManager.println("Элемент добавлен в коллекцию ");
                     return true;
                 } else {
                     outputManager.println("Здоровье не минимальное. Элемент не добавлен" + "\n");
                     return false;
                 }
             }
             else {
             outputManager.println("Поля элемента не валидны. Элемент не добавлен в коллекцию ");
             return false;
         }
     }
}
