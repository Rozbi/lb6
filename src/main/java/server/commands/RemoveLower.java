package server.commands;

import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.spaceMarine.SpaceMarine;
import lib.utility.Ask;

import java.util.PriorityQueue;

public class RemoveLower extends Command {
    private static String name;
    private static String description;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private InputManager inputManager;
    private Ask ask;

    public RemoveLower(String name, String description, OutputManager outputManager, CollectionManager collectionManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.name = name;
        this.description = description;
        this.ask = ask;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
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
        if (arg.isEmpty()) {
            outputManager.print("Неправильное количество аргументов ");
            return false;
        }
        if (collectionManager.getCollection().isEmpty()) {
            outputManager.println("Коллекция пуста ");
            return false;
        } else {
            PriorityQueue<SpaceMarine> queue = new PriorityQueue<>();

            for (var spaceMarine : collectionManager.getCollection()) {
                if (Long.parseLong(arg) <= spaceMarine.getId()) {
                    queue.add(spaceMarine);

                }
            } if (queue.size() == collectionManager.getCollection().size()){
                outputManager.println("У всех элементов id больше заданного ");
                return false;
            }collectionManager.setCollection(queue);
            outputManager.println("Элементы удалены");
            return true;
        }
    }
}
