package lib.utility;

import java.io.Serializable;

public class Message {
    String name;
    Serializable entity;
    public Message(String name, Serializable entity){
        this.name = name;
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", entity=" + entity +
                '}';
    }
}
