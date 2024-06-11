package server.managers;

import client.utility.*;
import lib.spaceMarine.Chapter;
import lib.spaceMarine.Coordinates;
import lib.spaceMarine.SpaceMarine;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.PriorityQueue;

import com.google.gson.reflect    .TypeToken;
import lib.managers.OutputManager;
import lib.utility.Message;
import lib.utility.MessageSerializer;
import server.exeptions.InvalidInputException;


/**менеджер для работы с файлом типа Json*/
public class JsonManager {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
            .registerTypeAdapter(Coordinates.class, new CoordinatesAdapter())
            .registerTypeAdapter(Chapter.class, new ChapterAdapter())
            .registerTypeAdapter(SpaceMarine.class, new SpaceMarineDeserializer())
            .registerTypeAdapter(Message.class, new MessageSerializer())
            .create();

    /**
     * метод сериализации коллекции в формат json
     */
    private String convertToJson(PriorityQueue<SpaceMarine> collection) {
        try {
            String jsonElement = gson.toJson(collection);
            return jsonElement.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * метод для десериализации json в коллекцию
     */
   /*@args
        String s - строки коллекции
        OutputManager outputManager - менеджер вывода
     */
    /*
     * @return - PriorityQueue<SpaceMarine> collection
     */
    private PriorityQueue<SpaceMarine> convertToCollection(String s) {

        Type collType = new TypeToken<PriorityQueue<SpaceMarine>>() {
        }.getType();
        try {
            PriorityQueue<SpaceMarine> result = gson.fromJson(s, collType);

        return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * запись коллекции в файл
     */
/*@args
        PriorityQueue<SpaceMarine> collection
     */
    public void writeCollection(PriorityQueue<SpaceMarine> collection) throws IOException {
        try {
            var jsonElement = convertToJson(collection);
            if (jsonElement == null) return;
            Writer writer = new FileWriter(System.getenv("CAPIPA"));
            try {
                writer.write(jsonElement);
                writer.flush();
                writer.close();
            } catch (RuntimeException e) {
            }
        } catch (IOException e) {
        }
    }

        /**
     * чтение коллекции из файла
     */
    /*
     * @return - коллекция
     */
    public Collection<SpaceMarine> readCollection() throws IOException {

        String jsonPath = System.getenv("CAPIPA");
        if (jsonPath == null || jsonPath.isEmpty())
            jsonPath = "test.json";

        try {
            File jsonFile = new File(jsonPath);

            if (!jsonFile.exists())
                jsonFile.createNewFile();

            if (!jsonFile.canRead()) {
                System.out.println("Нет доступа к чтению! Чтение недоступно! ");
                System.exit(1);
            }
            if (!jsonFile.canWrite()) {
                System.out.println("Нет доступа к записи! Сохранение недоступно! ");
                System.exit(1);
            }
            StringBuilder builder = new StringBuilder();
            try (BufferedReader buffer = new BufferedReader(new FileReader(jsonPath))) {
                String str;
                while ((str = buffer.readLine()) != null) {
                    builder.append(str).append("\n");
                }
                PriorityQueue<SpaceMarine> sm = convertToCollection(builder.toString());
                return sm;
            }
        } catch (IOException e) {
        }
        return null;
    }
    }
