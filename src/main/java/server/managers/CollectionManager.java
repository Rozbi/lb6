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
    private long currentId;
    private long id;
    private JsonManager jsonManager;
    private ArrayList<String> commands = new ArrayList<>(14);
    public CollectionManager(JsonManager jsonManager) throws InvalidInputException, IOException {
        this.jsonManager = jsonManager;
        collection.addAll(jsonManager.readCollection());
        long maxId = 0;
        if (collection.isEmpty()){
            currentId = 1;
        } else{
            for (var spaceMarine: collection){
                if (spaceMarine.getId()>maxId){
                    maxId = spaceMarine.getId();
                }
            } currentId = maxId+1;
        }
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
        System.out.println("Добавление элемента в коллекцию...");
        if (sm != null || sm.validate()) {
            collection.add(sm);
            currentId += 1;
            return true;
        } else {
            System.out.println("Неправильный элемент. Добавить в колекцию невозможно");
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
    /**сохранение коллекции*/
    public void save() throws IOException {
        jsonManager.writeCollection(collection);
        Comparator.naturalOrder();
        setLastSaveTime(LocalDateTime.now());
    }
    public PriorityQueue<SpaceMarine> getCollection(){
        return collection;
    }
    public long getCurrentId(){
        return currentId;
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
