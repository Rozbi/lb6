package client.managers;

import lib.managers.InputManager;
import lib.utility.PortGetter;
import server.exeptions.InvalidInputException;
import lib.managers.OutputManager;

import javax.sound.sampled.Port;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class UdpClient {
    private DatagramSocket datagramSocket;
    private InputManager inputManager;
    private OutputManager outputManager;
    byte[] buffer;
    private final PortGetter portGetter;
    private DatagramChannel datagramChannel;

    public UdpClient(InputManager inputManager, PortGetter portGetter, OutputManager outputManager) throws IOException {
        this.inputManager = inputManager;
        this.portGetter = portGetter;
        this.outputManager = outputManager;

    }

public void connect() {
    try {
        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", portGetter.getServerPort());
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false); // Устанавливаем неблокирующий режим
        datagramChannel.connect(serverAddress);   // Подключаем канал к адресу сервера

    } catch (IOException exception) {
        outputManager.printerr("Не удалось подключиться к серверу");
    }
}
    public DatagramChannel getChannel(){
        return this.datagramChannel;
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