package client;

import client.managers.*;
import client.utility.Runner;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import server.exeptions.InvalidInputException;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8080);
        OutputManager outputManager = new OutputManager();
        DatagramSocket datagramSocket = new DatagramSocket();
        InputManager inputManager = new InputManager(outputManager, scanner);
        UdpClient udpClient = new UdpClient(inputManager, inetSocketAddress, outputManager);
        JsonManager jsonManager = new JsonManager(outputManager);
        SendingManager sendingManager = new SendingManager(udpClient, jsonManager, outputManager);
        CommandManager commandManager = new CommandManager();
        Runner runner = new Runner(outputManager, commandManager, inputManager, udpClient, sendingManager);
        udpClient.newIP();
        runner.letsGo();
    }
}
