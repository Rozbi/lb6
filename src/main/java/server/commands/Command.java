package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import lib.utility.Executable;

public abstract class Command implements Executable {
    private String name;
    private String description;
    public Command(String name, String description){
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

     @Override
    public String toString(){
        return "Command{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
    /**
     * @return хеш код для поля имени и описания
     */
    @Override
    public int hashCode(){
        return Objects.hash(name,description);
    }
    /**
     * @param o - объект сравнения
     * @return сравнение объектов
     */

}
