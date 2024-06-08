package client.managers;


import server.exeptions.InvalidInputException;
import lib.utility.Message;
import lib.managers.OutputManager;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class SendingManager {
    private final int DATA_SIZE = 1024;
    private final UdpClient udpClient;
    private final JsonManager jsonManager;
    private final OutputManager outputManager;

    public SendingManager(UdpClient udpClient, JsonManager jsonManager, OutputManager outputManager) {
        this.udpClient = udpClient;
        this.jsonManager = jsonManager;
        this.outputManager = outputManager;
    }


    public void sendMessage(Message message) throws InvalidInputException {
        //var logger = LoggerManager.getLogger(SendingManager.class);
        var t = 10;
        for (; ; ) {
            try {
                while (!udpClient.isConnected()) {
                    outputManager.println("Ожидайте подключения..");
                    Thread.sleep(4000);
                }
                ByteBuffer buffer = ByteBuffer.wrap(jsonManager.gson.toJson(message).getBytes());
                InetSocketAddress address = udpClient.getHost();
                try {
                    udpClient.getChannel().send(buffer, address);
                } catch (Exception e) {
                    if (t-- < 0) {
                        outputManager.println("Ошибка отправки");
                        t = 10;
                        udpClient.newIP();
                    }
                }
                    try {
                        Thread.sleep(200);
                        udpClient.getChannel().close();
                        udpClient.connect();
                    } catch (Exception ex) {
                        outputManager.println("Ошибка подключения");
                    }
            } catch (InterruptedException e) {
                outputManager.printerr("Слишком долгое ожидание");
            }
        }
    }
}
