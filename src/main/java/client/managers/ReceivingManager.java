package client.managers;

import lib.managers.OutputManager;
import lib.utility.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

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

    // Создаем буфер для получения данных
    ByteBuffer buffer = ByteBuffer.allocate(4096);

    // Переводим канал в неблокирующий режим и пытаемся получить пакет
    InetSocketAddress senderAddress = null;
    while (senderAddress == null) {
        try {
            TimeUnit.MILLISECONDS.sleep(100); // Ждем немного, если данных пока нет
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Пытаемся прочитать данные из канала
        senderAddress = (InetSocketAddress) udpClient.getChannel().receive(buffer);
    }

    // Переключаем буфер в режим чтения
    buffer.flip();

    // Преобразуем данные из буфера в массив байтов для десериализации
    byte[] data = new byte[buffer.remaining()];
    buffer.get(data);

    try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
         ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {

        // Десериализуем объект
        return (Message) objectStream.readObject();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Deserialization error", e);
    }
}
}
