package lib.managers;

import server.exeptions.InvalidInputException;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**менеджер для чтения данных пользователя*/
public class InputManager {
    private Scanner scanner;
    private OutputManager outputManager;
    private boolean finish = false;
    private int flag;

    public InputManager(OutputManager outputManager, Scanner scanner) throws FileNotFoundException {
        this.outputManager = outputManager;
        this.scanner = scanner;

    }

    /**
     * чтение данных пользователя
     */
    public String read() throws InvalidInputException {
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim().toLowerCase();
            return input;
        }
        return null;
    }
}
