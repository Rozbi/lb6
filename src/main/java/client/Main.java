package client;

import client.managers.*;
import client.utility.Ask;
import client.utility.Runner;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.utility.PortGetter;
import server.exeptions.InvalidInputException;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        PortGetter portGetter = new PortGetter();
        OutputManager outputManager = new OutputManager();
        InputManager inputManager = new InputManager(scanner);
        Ask ask = new Ask(inputManager, outputManager);
        UdpClient udpClient = new UdpClient(inputManager, portGetter, outputManager);
        JsonManager jsonManager = new JsonManager(outputManager);
        SendingManager sendingManager = new SendingManager(udpClient, jsonManager, outputManager, new InetSocketAddress("127.0.0.1", portGetter.getServerPort()));
        CommandManager commandManager = new CommandManager();
        ReceivingManager receivingManager = new ReceivingManager(udpClient, jsonManager, outputManager);

        Runner runner = new Runner(outputManager, commandManager, inputManager, udpClient, sendingManager, ask, receivingManager);
        udpClient.connect();
        runner.letsGo();
    }
}
