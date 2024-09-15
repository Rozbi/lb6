package lib.utility;

import java.io.Serializable;
import java.net.InetSocketAddress;

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


    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", entity=" + entity + ", address=" + address +
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
