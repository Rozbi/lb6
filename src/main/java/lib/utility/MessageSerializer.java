package lib.utility;

import com.google.gson.*;
import lib.utility.Message;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;

public class MessageSerializer implements JsonDeserializer<Message> {
    @Override
    public Message deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Serializable name = jsonObject.get("name").getAsString();
        var entityObject = jsonObject.get("entity");
        var userObject = jsonObject.get("user");
        Serializable user = null;
        Serializable entity = null;
        if (entityObject != null)
            entity = entityObject.getAsString();
            if (userObject != null) {
                user = jsonDeserializationContext.deserialize(jsonObject.get("user"), User.class);
                try {
                    return new Message(name.toString(), entity, (User) user);
                } catch (Exception e) {
                    System.out.println("Некорректные данные в сохранении. " + e.getMessage());
                    System.exit(1);
                }
            }
        return new Message(name.toString(), entity);

    }
}
