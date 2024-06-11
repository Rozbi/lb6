package lib.managers;

import server.exeptions.InvalidInputException;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**менеджер для чтения данных пользователя*/
public class InputManager {
    private static Scanner scanner;
    private boolean finish = false;
    private int flag;

    public InputManager(Scanner scanner) throws FileNotFoundException {
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
    public static Scanner getScanner(){
        return scanner;
    }
}
