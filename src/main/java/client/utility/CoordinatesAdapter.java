package client.utility;

import com.google.gson.*;
import lib.spaceMarine.Coordinates;

import java.lang.reflect.Type;

public class CoordinatesAdapter implements JsonDeserializer<Coordinates> {
        @Override
    public Coordinates deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Long x = jsonObject.get("x").getAsLong();
        float y = jsonObject.get("y").getAsInt();
        try {
            return new Coordinates(x, y);
        }
        catch (Exception e) {
            System.out.println("Некорректные данные в сохранении. " + e.getMessage());
            System.exit(1);

        }
        return null;

    }
}
