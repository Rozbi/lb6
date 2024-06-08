package server.utility;
import lib.utility.Runnable;
import lib.managers.InputManager;
import server.exeptions.InvalidInputException;
import server.managers.*;
import lib.managers.OutputManager;
import server.managers.ServerReceivingManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**класс для вызова команд*/
public class Runner implements Runnable {
    private OutputManager outputManager;
    private InputManager inputManager;
    private CommandManager comman;
    private ServerReceivingManager serverReceivingManager;
    private final JsonManager jsonManager;

    public Runner(OutputManager outputManager, InputManager inputManager, CommandManager commandManager, ServerReceivingManager serverReceivingManager, JsonManager jsonManager) {
        this.serverReceivingManager = serverReceivingManager;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.comman = commandManager;
        this.jsonManager = jsonManager;
    }

    /**
     * метод для запуска интерактивного режима
     **/
    @Override
    public void letsGo() {
        outputManager.print("Введите название команды или команду help для просмотра доступных команд\n");
        while (true) {
            try {
                String[] input = ((serverReceivingManager.receive(jsonManager)).toString().trim()+" ").split(" ", 2);
                String commandName = input[0];
                switch (commandName) {
                    case "execute_script": {
                        String argument = input[1].trim();
                        comman.execute(commandName, argument);
                        break;
                    }
                    default: {
                        if (input[1].isEmpty()) {
                            input[1] = "";
                            comman.execute(commandName, input[1]);
                            break;
                        } else {
                            String argument = input[1].trim();
                            ;
                            try {
                                Integer.parseInt(argument);
                            } catch (NumberFormatException e) {
                                outputManager.println("Неправильный аргумент");
                                break;
                            }
                            comman.execute(commandName, argument);
                            break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                outputManager.println("Файл скрипта не найден!");
                break;
            } catch (InvalidInputException e) {
                outputManager.println("Неверный ввод данных ");
            } catch (NullPointerException e) {
                outputManager.println("Давайте не будем так делать :( \n");
                break;
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

