package server.managers;
import lib.managers.OutputManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerReceivingManager {
    private final OutputManager outputManager;
    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;
    private final byte[] buffer = new byte[1024];

    public ServerReceivingManager(DatagramSocket datagramSocket, OutputManager outputManager) {
        this.datagramSocket = datagramSocket;
        this.outputManager = outputManager;
    }

    public DatagramPacket receive(){
        while(true){
            try{
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                this.datagramPacket = datagramPacket;
                datagramSocket.receive(datagramPacket);
                InetAddress address = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                outputManager.println("Message from Client: " +  message);
                datagramPacket = new DatagramPacket(buffer, buffer.length, address, port);
                return datagramPacket;
            } catch (IOException e) {
                outputManager.println("Ошибка принятия данных сервером ");
                break;
            }
        } return null;
    }
    public DatagramSocket getDatagramSocket(){
        return datagramSocket;
    }
    public InetAddress getAddress(){
        return datagramSocket.getInetAddress();
    }
}
