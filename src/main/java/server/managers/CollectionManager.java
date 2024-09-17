package server.managers;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import lib.spaceMarine.SpaceMarine;
import server.exeptions.InvalidInputException;
import server.managers.JsonManager;

/**менеджер для работы с коллекцией*/
public class CollectionManager {
    private PriorityQueue<SpaceMarine> collection = new PriorityQueue<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private SQLManager sqlManager;
    private ArrayList<String> commands = new ArrayList<>(14);
    public CollectionManager(SQLManager sqlManager) throws InvalidInputException, IOException {
        this.sqlManager=sqlManager;
        collection.addAll(sqlManager.select());
    }

    public void setCollection(PriorityQueue<SpaceMarine> collection) {
        this.collection = collection;
    }
    /**добавление элемента в коллекцию*/
    /*@args
        String commands.spaceMarine - элемент коллекции
     */
    /*
     * @return - commands
     */
    public boolean add(SpaceMarine sm) {
        if (sm != null || sm.validate()) {
            collection.add(sm);
            return true;
        } else {
            return false;
        }
    }
    public void setLastSaveTime(LocalDateTime lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }
    public void setLastInitTime(LocalDateTime lastInitTime) {
        this.lastInitTime = lastInitTime;
    }
    /**очистка коллекции*/
    public void clear(){
        collection.clear();
    }
    public PriorityQueue<SpaceMarine> getCollection(){
        return collection;
    }

    /**добавление названия команды в историю*/
    /*@args
        String p - название команды
     */
    /*
     * @return - commands
     */
    public ArrayList<String> history(String p){
        commands.add(p);
        return commands;
    }
    public ArrayList<String> getHistory () {
        return commands;
    }
}
