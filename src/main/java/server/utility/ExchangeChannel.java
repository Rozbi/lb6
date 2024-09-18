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

public class ExchangeChannel implements Runnable{

    private InetSocketAddress target;
    private DatagramChannel channel;
    private Selector selector;
    private ServerConnector connector;
    private CommandManager commandManager;
    // Пул потоков для многопоточного чтения запросов
    private final ExecutorService readThreadPool = Executors.newCachedThreadPool();

    // Пул потоков для многопоточной обработки запросов
    private final ForkJoinPool processPool = new ForkJoinPool();

    public ExchangeChannel(ServerConnector connector) {
        this.connector = connector;
    }

@Override
     public void run() {
    try {
        connector.connect();
        selector = connector.getSelector();
        while (true) {
            selector.selectNow();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isReadable()) {
                    DatagramChannel datagramChannel = (DatagramChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(4000);
                    InetSocketAddress clientAddress = (InetSocketAddress) datagramChannel.receive(buffer);
                    new Thread(() -> {
                        try {
                            handleRead(datagramChannel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    } catch (IOException e) {
        throw new RuntimeException("ERROR PANIC ERROR", e);
    }
}

//public boolean sendMessage(DatagramChannel clientChannel, Message message) {
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



   private void handleRead(DatagramChannel channel) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(4000);

    InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

    if (clientAddress != null) {
        buffer.flip();  // ЧИТАТЬ О НЕТ

        readThreadPool.execute(() -> {
            Message message = extractMessage(buffer);
            if (message != null) {
                processPool.execute(() -> {
                    try {
                        processMessage(message);
                    } catch (InvalidInputException | IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
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