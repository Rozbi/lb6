package client.managers;


import server.exeptions.InvalidInputException;
import lib.utility.Message;
import lib.managers.OutputManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import org.apache.commons.lang3.SerializationUtils;

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


    public void sendMessage(Message message) throws InvalidInputException, IOException {
        ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
        udpClient.getChannel().send(buffer, host);
    }
}
