package client.managers;


import server.exeptions.InvalidInputException;
import lib.utility.Message;
import lib.managers.OutputManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class SendingManager {
    private final UdpClient udpClient;
    private final JsonManager jsonManager;
    private final OutputManager outputManager;
    private int port;
    private SocketAddress host;


    public SendingManager(UdpClient udpClient, JsonManager jsonManager, OutputManager outputManager, SocketAddress host) {
        this.udpClient = udpClient;
        this.jsonManager = jsonManager;
        this.outputManager = outputManager;
        this.host = host;
    }


    public void sendMessage(Message message) throws InvalidInputException {
        ByteBuffer buffer = ByteBuffer.wrap(jsonManager.gson.toJson(message).getBytes());
        try {
            udpClient.getSocket().send(new DatagramPacket(buffer.array(), buffer.array().length, host));
        } catch (Exception e) {
            outputManager.printerr("Ошибка отправки");
        }
    }
}
