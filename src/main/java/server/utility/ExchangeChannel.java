package server.utility;

import java.io.IOException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Executable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import lib.utility.Message;
import lib.utility.MessageSerializer;
import lib.utility.User;
import server.commands.Command;
import server.exeptions.InvalidInputException;
import server.managers.*;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ExchangeChannel {
    private InetSocketAddress target;
    private DatagramChannel channel;
    private Selector selector;
    private ServerConnector connector;
    private CommandManager commandManager;
    // Пул потоков для многопоточного чтения запросов
    private final ExecutorService readThreadPool = Executors.newCachedThreadPool();

    // Пул потоков для многопоточной обработки запросов
    private final ForkJoinPool processPool = new ForkJoinPool();
    private final ExecutorService sendThreadPool = Executors.newFixedThreadPool(10);

    public ExchangeChannel(InetSocketAddress target) {
        this.target = target;
    }


     public void start() {
    // Создаем серверный сокет один раз
    try (DatagramSocket serverSocket = new DatagramSocket()) {
        while (true) {
            selector = Selector.open();
            // Ожидаем, пока есть доступные ключи для селектора
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isReadable()) {
                    byte[] bufferData = new byte[4000];
                    DatagramPacket packet = new DatagramPacket(bufferData, bufferData.length);
                    serverSocket.receive(packet);
                    new Thread(() -> {
                        try {
                            handleRead(serverSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    } catch (IOException e) {
        throw new RuntimeException("Ошибка в работе сокета", e);
    }
}
//public boolean sendMessage(DatagramSocket clientChannel, Message message) {
//        sendThreadPool.execute(() -> {
//            ByteBuffer buffer = ByteBuffer.wrap(MessageSerializer.serialize(message));
//            try {
//                while (buffer.hasRemaining()) {
//                    clientChannel.write(buffer);
//                }
//            } catch (IOException e) {
//                System.out.println("Ошибка при отправке сообщения: " + e.getMessage());
//            }
//        });
//        return true; // Возвращаем true, чтобы указать, что задача отправки добавлена в пул потоков
//    }
//}

    private void handleRead(DatagramSocket serverSocket) throws IOException {
    byte[] bufferData = new byte[4000];
    DatagramPacket packet = new DatagramPacket(bufferData, bufferData.length);

    serverSocket.receive(packet);

    ByteBuffer buffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
    readThreadPool.execute(() -> {
        Message message = extractMessage(buffer);
        if (message != null) {
            processPool.execute(() -> {
                try {
                    processMessage(message);
                } catch (InvalidInputException e) {
                } catch (IOException e) {
                }
            });
        }
    });
}
    private Message extractMessage(ByteBuffer buffer) {
        try {
            return new Message(buffer.array());
        } catch (StreamCorruptedException e) {
            //throw new RuntimeException(e);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void processMessage(Message message) throws InvalidInputException, IOException {
        String commandName = message.getName();
        Serializable entity = message.getEntity();
        User user = message.getUser();
        boolean result = launchCommand(message);
    }
    private boolean launchCommand(Message message) throws InvalidInputException, IOException {
        Command command = commandManager.getCommandMap().get(message.getName());
        if (!command.execute(message)) return false;
        return true;
    }
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}