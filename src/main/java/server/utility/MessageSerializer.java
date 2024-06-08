package server.utility;

import com.google.gson.*;
import lib.utility.Message;

import java.io.Serializable;
import java.lang.reflect.Type;

public class MessageSerializer implements JsonDeserializer<Message> {
    @Override
    public Message deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        Serializable entity = jsonObject.get("entity").getAsString();
        try {
            return new Message(name, entity);
        } catch (Exception e) {
            System.out.println("Некорректные данные в сохранении. " + e.getMessage());
            System.exit(1);
        }
        return null;

    }
}
