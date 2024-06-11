package client.managers;

import lib.managers.OutputManager;
import lib.utility.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class ReceivingManager {
    private final UdpClient udpClient;
    private final JsonManager jsonManager;
    private final OutputManager outputManager;

    public ReceivingManager(UdpClient udpClient, JsonManager jsonManager, OutputManager outputManager) {
        this.udpClient = udpClient;
        this.jsonManager = jsonManager;
        this.outputManager = outputManager;
    }

    public Message receive() throws IOException {
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        udpClient.getSocket().receive(packet);
        ByteArrayInputStream n = new ByteArrayInputStream(packet.getData());
        ObjectInputStream o = new ObjectInputStream(n);
        try {
            Message message = (Message) o.readObject();
            return  message;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deserialization error", e);
        }
    }

}
