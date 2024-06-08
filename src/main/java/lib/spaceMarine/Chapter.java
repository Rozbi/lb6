package lib.spaceMarine;

import lib.utility.Validatable;

import java.util.Objects;
/**класс главы*/
public class Chapter implements Validatable {
        private String name; //Поле не может быть null, Строка не может быть пустой
        private String world; //Поле может быть null
public Chapter(String name, String world){
    this.name = name;
    this.world = world;
}
public Chapter(String s) {
        try {
            this.name = s.split(" ; ")[0];
            this.world = s.split(";")[1];
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    @Override
    public boolean validate() {
        if (name == null && name.isEmpty()) {
            return false;
        } return (world.isEmpty());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(name, chapter.name) && Objects.equals(world, chapter.world);
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", world='" + world + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, world);
    }
}


