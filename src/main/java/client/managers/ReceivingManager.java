package client.managers;

import lib.managers.OutputManager;
import lib.utility.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SerializationUtils;

public class ReceivingManager {
    private final UdpClient udpClient;
    private final JsonManager jsonManager;
    private final OutputManager outputManager;

    public ReceivingManager(UdpClient udpClient, JsonManager jsonManager, OutputManager outputManager) {
        this.udpClient = udpClient;
        this.jsonManager = jsonManager;
        this.outputManager = outputManager;
    }

   public Message receive() throws IOException, SocketTimeoutException {
    ByteBuffer buffer = ByteBuffer.allocate(4096);
    InetSocketAddress senderAddress = null;

    while (senderAddress == null) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            senderAddress = (InetSocketAddress) udpClient.getChannel().receive(buffer);
        }catch(PortUnreachableException e){

        }
    }

    buffer.flip(); // Переключаем в режим чтения.

    int bytesRead = buffer.remaining();
    if (bytesRead <= 0) {
        throw new IOException("No data received");
    }

    byte[] data = new byte[bytesRead];
    buffer.get(data);
    Message message = SerializationUtils.deserialize(buffer.array());

    return message;
}
}
