package server.managers;

import lib.managers.OutputManager;

import java.io.IOException;
import java.net.DatagramPacket;

public class ServerSendingManager {
    private ServerReceivingManager serverReceivingManager;
    private OutputManager outputManager;
    private byte[] buffer;

    public ServerSendingManager(ServerReceivingManager serverReceivingManager, OutputManager outputManager, byte[] buffer) {
        this.serverReceivingManager = serverReceivingManager;
        this.outputManager = outputManager;
        this.buffer = buffer;
    }
    public void sendPack() {
        while (true) {
            try {
                DatagramPacket datagramPacket = serverReceivingManager.receive();
                serverReceivingManager.getDatagramSocket().send(datagramPacket);
            } catch (IOException e) {
                outputManager.println("Ошибка отправки ");
            }
        }
    }
}
