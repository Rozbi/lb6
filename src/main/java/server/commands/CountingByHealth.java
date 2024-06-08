package server.commands;

import server.managers.CollectionManager;
import server.managers.JsonManager;
import lib.managers.OutputManager;

import java.util.*;

public class CountingByHealth extends Command {
    private static String name;
    private static String description;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private JsonManager jsonManager;
    public CountingByHealth(String name, String description, CollectionManager collectionManager, OutputManager outputManager) {
        super("group_counting_by_health", "сгруппировать элементы коллекции по значению поля health, вывести количество элементов в каждой группе");this.name = name;
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
        } HashMap<Long, Integer> mapa= new HashMap<>();
        outputManager.println("health: count");
        for (var spaceMarine : collectionManager.getCollection()){
            if (mapa.containsKey(spaceMarine.getHealth())){
                mapa.put(spaceMarine.getHealth(), mapa.get(spaceMarine.getHealth())+1);
            } else{
                mapa.put(spaceMarine.getHealth(), 1);
            }
        } for (Long key:mapa.keySet()){
            outputManager.println((key).toString()+": "+mapa.get(key).toString() + "\n");
         }
        return true;
    }
}
