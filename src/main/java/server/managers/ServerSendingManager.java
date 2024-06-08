package server.managers;

import lib.managers.OutputManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;

import java.io.IOException;
import java.net.DatagramPacket;

public class ServerSendingManager {
    private ServerReceivingManager serverReceivingManager;
    private final OutputManager outputManager;
    private final JsonManager jsonManager;
    private byte[] buffer;

    public ServerSendingManager(ServerReceivingManager serverReceivingManager, OutputManager outputManager, byte[] buffer, JsonManager jsonManager) {
        this.serverReceivingManager = serverReceivingManager;
        this.outputManager = outputManager;
        this.buffer = buffer;
        this.jsonManager = jsonManager;
    }
//      public void sendMessage(Message message) throws InvalidInputException {
//        //var logger = LoggerManager.getLogger(SendingManager.class);
//        var t = 10;
//        for (; ; ) {
//            try {
//                while (!udpClient.isConnected()) {
//                    outputManager.println("Ожидайте подключения..");
//                    Thread.sleep(4000);
//                }
//                ByteBuffer buffer = ByteBuffer.wrap(jsonManager.gson.toJson(message).getBytes());
//                InetSocketAddress address = udpClient.getHost();
//                try {
//                    udpClient.getChannel().send(buffer, address);
//                } catch (Exception e) {
//                    if (t-- < 0) {
//                        outputManager.println("Ошибка отправки");
//                        t = 10;
//                        udpClient.newIP();
//                    }
//                }
//                    try {
//                        Thread.sleep(200);
//                        udpClient.getChannel().close();
//                        udpClient.connect();
//                    } catch (Exception ex) {
//                        outputManager.println("Ошибка подключения");
//                    }
//            } catch (InterruptedException e) {
//                outputManager.printerr("Слишком долгое ожидание");
//            }
//        }
//    }
}
