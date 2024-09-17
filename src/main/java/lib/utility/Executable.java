package lib.utility;

import server.exeptions.InvalidInputException;

import java.io.IOException;

public interface Executable {
    boolean execute(Message message) throws InvalidInputException, IOException;
}
