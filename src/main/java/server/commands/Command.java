package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Command {
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
    public boolean execute(Message message) throws IOException, InvalidInputException {
        return false;
    }
}
