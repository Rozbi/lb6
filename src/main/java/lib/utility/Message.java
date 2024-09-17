package lib.utility;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Message implements Serializable {
    String name;
    Serializable entity;
    InetSocketAddress address;
    User user;
    public Message(String name, Serializable entity, InetSocketAddress address) {
        this.name = name;
        this.entity = entity;
        this.address = address;
    }
    public Message(String name, Serializable entity) {
        this.name = name;
        this.entity = entity;
    }
     public Message(String name, Serializable entity, User user) {
        this.name = name;
        this.entity = entity;
        this.user = user;
    }
     public Message(String name, Serializable entity, User user, InetSocketAddress address) {
        this.name = name;
        this.entity = entity;
        this.user = user;
        this.address = address;
    }
      public Message(String name, User user) {
        this.name = name;
        this.user = user;
    }
    public Message(byte[] bytes) throws StreamCorruptedException, IOException {

        ObjectInputStream in = null;

        in = new ObjectInputStream(new ByteArrayInputStream(bytes));

        Message message = null;
        try {
            message = (Message)in.readObject();
        }
        catch (EOFException e) {
            message = null;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.entity = message.entity;
        this.name = message.name;
        this.user = message.user;



    }

    public Message(ByteBuffer buffer) {

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Message message = null;
        try {
            message = (Message)in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.entity = message.entity;
        this.name = message.name;
        this.user = message.user;


    }


   @Override
public String toString() {
    return "Message{" +
           "name='" + name + '\'' +
           (entity == null ? "null" : "entity=" + entity) + '\'' +
           (user == null ? "null" : "user=" + user.toString()) + '\'' +
           "address=" + address +
           '}';
}
    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public Serializable getEntity() {
        return entity;
    }
    public InetSocketAddress getAddress() {
        return address;
    }
    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }
}
