import client.managers.*;
import lib.managers.InputManager;
import lib.utility.Ask;
import lib.managers.OutputManager;
import server.managers.*;
import server.managers.JsonManager;
import server.utility.Runner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        byte[] buffer = new byte[300];
        OutputManager outputManager = new OutputManager();
        JsonManager jsonManager = new JsonManager(outputManager);
        CollectionManager collectionManager = new CollectionManager(jsonManager);
        InputManager inputManager = new InputManager(outputManager, new Scanner(System.in));
        Ask ask = new Ask(inputManager, outputManager);
        ServerConnector serverConnector = new ServerConnector(inputManager, new InetSocketAddress("127.0.0.1", 1235), outputManager);
        ServerReceivingManager serverReceivingManager = new ServerReceivingManager(serverConnector, outputManager);
//        CommandManager = new CommandManager(collectionManager, outputManager, ask, inputManager, serverReceivingManager);
        ServerSendingManager serverSendingManager = new ServerSendingManager(serverReceivingManager, outputManager, buffer, jsonManager);
        serverConnector.connect();
        serverReceivingManager.receive(jsonManager);
//        Runner runner = new Runner(outputManager, inputManager, commandManager, serverReceivingManager);
//        runner.letsGo();
//        serverSendingManager.sendPack();


    }

}
