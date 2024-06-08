package client.utility;

import com.google.gson.*;
import lib.spaceMarine.Chapter;

import java.lang.reflect.Type;

public class ChapterAdapter implements JsonDeserializer<Chapter> {
    @Override
    public Chapter deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        String world = jsonObject.get("world").getAsString();
        try {
            return new Chapter(name, world);
        }
        catch (Exception e) {
            System.out.println("Некорректные данные в сохранении. " + e.getMessage());
            System.exit(1);
        }
        return null;

    }
}
