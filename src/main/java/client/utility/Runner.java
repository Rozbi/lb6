package client.utility;
import client.managers.ReceivingManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import client.managers.CommandManager;
import lib.managers.InputManager;
import client.managers.SendingManager;
import client.managers.UdpClient;
import lib.managers.OutputManager;
import lib.utility.Runnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**класс для вызова команд*/
public class Runner implements Runnable {
    private OutputManager outputManager;
    private InputManager inputManager;
    private SendingManager sendingManager;
    private UdpClient udpClient;
    private final CommandManager commandManager;
    private ReceivingManager receivingManager;

    public Runner(OutputManager outputManager, CommandManager commandManager, InputManager inputManager, UdpClient udpClient, SendingManager sendingManager, Ask ask, ReceivingManager receivingManager) {
        this.sendingManager = sendingManager;
        this.receivingManager = receivingManager;
        this.udpClient = udpClient;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.commandManager = commandManager;
    }
    /**
     * метод для запуска интерактивного режима
     **/
    @Override
    public void letsGo() throws IOException, InvalidInputException {
        commandManager.addCommands();
        while (true) {
            outputManager.print("Выберите действие. Login/Register\n");
            String input = inputManager.read().toLowerCase();
            String[] command = (input.trim() + " ").split(" ", 2);
            String name = command[0];
            if (name.equals("login") || (name.equals("register"))) {
                outputManager.print("Введите логин\n");
                String loginInput = inputManager.read().toLowerCase();
                String[] inputTrim = (loginInput.trim() + " ").split(" ", 2);
                String login = inputTrim[0];
                outputManager.print("Введите пароль\n");
                String PsswdInput = inputManager.read().toLowerCase();
                String[] PsswdTrim = (PsswdInput.trim() + " ").split(" ", 2);
                String psswd = PsswdTrim[0];
                Message userMessage = new Message(name, login + " " + psswd);
                sendingManager.sendMessage(userMessage);
                String recievedMessage = receivingManager.receive().toString();
                String nameMessage = recievedMessage.split(" ", 2)[0];
                switch (nameMessage) {
                    case "SuccessLogin":
                        outputManager.print("Введите название команды или команду help для просмотра доступных команд\n");
                        while (true) {
                            try {
                                String letsGoInput = inputManager.read().toLowerCase();
                                String[] letsGoCommand = (letsGoInput.trim() + " ").split(" ", 2);
                                String letsGoName = letsGoCommand[0];
                                if (!commandManager.getCommandMap().containsKey(letsGoName)) {
                                    outputManager.printerr("Такой команды не существует");
                                    continue;
                                }
                                if ((!(commandManager.getCommandMap().get(letsGoName)) && !(letsGoCommand[1].isEmpty())) || ((commandManager.getCommandMap().get(letsGoName)) && (letsGoCommand[1].isEmpty()))) {
                                    outputManager.printerr("Неправильное количество аргументов!");
                                    continue;
                                }
                                switch (letsGoName) {
                                    case "execute_script": {
                                        String argument = letsGoCommand[1].trim();
                                        letsGoScript(new InputManager(new Scanner(new File(argument))));
                                    }
                                    case "add", "add_if_min", "update": {
                                        Ask ask = new Ask(inputManager, outputManager);
                                        String argument = ask.getSpaceMarineComponents();
                                        String idArgument = letsGoCommand[1];
                                        Message message = new Message(letsGoName, idArgument + argument);
                                        sendingManager.sendMessage(message);
                                        outputManager.prettyPrint(receivingManager.receive());
                                        break;
                                    }
                                    case "exit": {
                                        Message message = new Message(letsGoName, "");
                                        sendingManager.sendMessage(message);
                                        outputManager.prettyPrint(receivingManager.receive());
                                        System.exit(0);
                                    }
                                    default: {
                                        if (letsGoCommand[1].isEmpty()) {
                                            letsGoCommand[1] = "";
                                            Message message = new Message(letsGoName, letsGoCommand[1]);
                                            udpClient.connect();
                                            sendingManager.sendMessage(message);
                                            outputManager.prettyPrint(receivingManager.receive());
                                            break;
                                        } else {
                                            String argument = letsGoCommand[1].trim();
                                            try {
                                                Integer.parseInt(argument);
                                            } catch (NumberFormatException e) {
                                                outputManager.printerr("Неправильный аргумент");
                                                break;
                                            }
                                            Message message = new Message(letsGoName, argument);
                                            sendingManager.sendMessage(message);
                                            outputManager.prettyPrint(receivingManager.receive());
                                            break;
                                        }


                                    }
                                }
                            } catch (SocketTimeoutException e) {
                                throw new RuntimeException(e);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                }
            }
        }
    }

        /**
         * метод для запуска интерактивного режима в execute_script
         */
        public void letsGoScript (InputManager inputManager){

            commandManager.addCommands();
            while (InputManager.getScanner().hasNextLine()) {
                try {
                    String input;
                    input = inputManager.read().toLowerCase();
                    String[] command = (input.trim() + " ").split(" ", 2);
                    String commandName = command[0];
                    if (!commandManager.getCommandMap().containsKey(commandName)) {
                        System.out.println("Такой команды не существует");
                    }
                    if (((commandManager.getCommandMap().get(commandName)) && (command[1].isEmpty())) || (!(commandManager.getCommandMap().get(commandName)) && !(command[1].isEmpty()))) {
                        System.out.println("Неправильное количество аргументов!");
                    }
                    if (!commandName.equals("execute_script")) {
                        if (command[1].isEmpty()) {
                            command[1] = "";
                            Message message = new Message(commandName, command[1]);
                            sendingManager.sendMessage(message);
                            Message serverMessage = receivingManager.receive();
                            outputManager.print(serverMessage.toString());
                            break;
                        } else {
                            String argument = command[1].trim();
                            try {
                                Integer.parseInt(argument);
                                Message message = new Message(commandName, argument);
                                sendingManager.sendMessage(message);
                                Message serverMessage = receivingManager.receive();
                                outputManager.print(serverMessage.toString());
                                break;
                            } catch (NumberFormatException e) {
                                outputManager.println("Неправильный аргумент");
                            } catch (SocketTimeoutException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        outputManager.println("Скрипт не может вызываться рекурсивно");
                    }
                } catch (InvalidInputException e) {
                    outputManager.printerr("Неверный ввод данных ");
                } catch (NullPointerException e) {
                } catch (SocketTimeoutException e) {
                    outputManager.printerr("Время ожидания вышло.");
                } catch (IOException e) {
                }
            }
        }
    }


