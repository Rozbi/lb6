package lib.utility;

import client.managers.CommandManager;
import lib.managers.InputManager;
import lib.managers.OutputManager;

import java.util.Scanner;

public interface Runnable {
    /**
     * интерфейс для запуска интерактивного режима
     */
    public void letsGo();

    public void letsGoScript(String arg, CommandManager comman, InputManager inputManager, OutputManager outputManager, Scanner scanner);
}