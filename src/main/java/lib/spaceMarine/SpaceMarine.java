package lib.spaceMarine;


import lib.utility.Validatable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
/**основной класс лабораторной работы
 */
public class SpaceMarine implements Validatable, Serializable,Comparable<SpaceMarine>{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long health; //Значение поля должно быть больше 0
    private int heartCount; //Значение поля должно быть больше 0, Максимальное значение поля: 3
    private AstartesCategory category; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле может быть null
    private Chapter chapter; //Поле может быть null

    public SpaceMarine(long id, String name, Coordinates coordinates, Long health, int heartCount, AstartesCategory category, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.name = name;
        this.id = id;
        this.health = health;
        this.creationDate = LocalDateTime.now();
        this.category = category;
        this.heartCount = heartCount;
        this.coordinates = coordinates;
        this.chapter = chapter;
        this.meleeWeapon = meleeWeapon;
    }
    public SpaceMarine(long id, String name, Coordinates coordinates, LocalDateTime creationDate, Long health, int heartCount, AstartesCategory category, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.name = name;
        this.id = id;
        this.health = health;
        this.creationDate = creationDate;
        this.category = category;
        this.heartCount = heartCount;
        this.coordinates = coordinates;
        this.chapter = chapter;
        this.meleeWeapon = meleeWeapon;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(Long health) {
        this.health = health;
    }

    public void setHeartCount(int heartCount) {
        this.heartCount = heartCount;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getHealth() {
        return health;
    }

    public int getHeartCount() {
        return heartCount;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }
      /**
     * @return true, если поля удовлетворяют условиям, иначе false
     */
      @Override
    public boolean validate() {
        if (id <= 0) { return false; }
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (coordinates == null || !coordinates.validate()) {
            return false;
        }
        if (creationDate == null) {
            return false;
        }
        if (health <= 0){
            return false;
        }
        if (heartCount <= 0|| heartCount > 3){
            return false;
        }
        if (chapter != null && chapter.validate()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine that = (SpaceMarine) o;
        return id == that.id && health == that.health && heartCount == that.heartCount && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && category == that.category && meleeWeapon == that.meleeWeapon && Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, health, heartCount, category, meleeWeapon, chapter);
    }

    @Override
    public String toString() {
        return "commands.spaceMarine.SpaceMarine{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
            "\"creationDate\": \"" + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
            "\"health\": \"" + health + "\", " +"\"heartCount\": \"" + heartCount + "\", " +
            "\"category\": " + ( category == null ? "null " : category + " ") +
                "\"meleeWeapon\": " + ( meleeWeapon == null ? "null " : meleeWeapon + " ") +
                "\"chapter\": " + ( chapter == null ? "null " : "\""+ chapter.toString() +"\"") + "}" + "\n";
    }
    public static String[] toArray(SpaceMarine e) {
        var list = new ArrayList<String>();
        list.add(String.valueOf(e.getId()));
        list.add(e.getName());
        list.add(e.getCoordinates().toString());
        list.add(e.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        list.add(e.getHealth().toString());
        list.add(String.valueOf(e.getHeartCount()));
        list.add(e.getCategory() == null ? "null" : e.getCategory().toString());
        list.add(e.getMeleeWeapon() == null ? "null" : e.getMeleeWeapon().toString());
        list.add(e.getChapter() == null ? "null": e.getMeleeWeapon().toString());
        return list.toArray(new String[0]);
    }

    @Override
    public int compareTo(SpaceMarine o) {
        return (int)(this.id - o.getId());
    }
}