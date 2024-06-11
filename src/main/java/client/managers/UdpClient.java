package client.managers;

import lib.managers.InputManager;
import lib.utility.PortGetter;
import server.exeptions.InvalidInputException;
import lib.managers.OutputManager;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;

public class UdpClient {
    private DatagramSocket datagramSocket;
    private InputManager inputManager;
    private OutputManager outputManager;
    byte[] buffer;
    private final PortGetter portGetter;

    public UdpClient(InputManager inputManager, PortGetter portGetter, OutputManager outputManager) throws IOException {
        this.inputManager = inputManager;
        this.portGetter = portGetter;
        this.outputManager = outputManager;
    }


    public boolean isConnected() {
        return datagramSocket.isConnected();
    }

    public void connect() throws SocketException {
        if (datagramSocket != null) {
            datagramSocket.close();
        }
        try {
            datagramSocket = new DatagramSocket(portGetter.getClientPort());
        } catch (IOException e) {
            outputManager.printerr("Не удалось подключиться к хосту!");
        }
    }
    public DatagramSocket getSocket(){
        return this.datagramSocket;
    }

    public InetSocketAddress newIP() throws InvalidInputException {
        outputManager.println("Напишите новый хост (address:port):");
        while (true) {
            var adr = inputManager.read();
            if (adr.contains(":")) {
                try {
                    var port = Integer.parseInt(adr.split(":")[1]);
                    datagramSocket.close();
                    return new InetSocketAddress(adr.split(":")[0], port);
                } catch (Exception e1) {
                    outputManager.println("Попробуйте снова");
                }
            } else {
                try {
                    datagramSocket.close();
                    return new InetSocketAddress(adr, portGetter.getClientPort());
                } catch (Exception e) {
                    outputManager.println("Неправильный ввод. Напишите новый хост (address:port):");
                }
            }
            return null;
        }
    }
}