package client.managers;

import client.utility.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lib.managers.OutputManager;
import lib.spaceMarine.Chapter;
import lib.spaceMarine.Coordinates;
import lib.spaceMarine.SpaceMarine;
import lib.utility.Message;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.PriorityQueue;


/**менеджер для работы с файлом типа Json*/
public class JsonManager {
    private OutputManager outputManager;

    private int currentId = 1;

    public JsonManager(OutputManager outputManager) {
        this.outputManager = outputManager;
    }

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
            .registerTypeAdapter(Coordinates.class, new CoordinatesAdapter())
            .registerTypeAdapter(Chapter.class, new ChapterAdapter())
            .registerTypeAdapter(SpaceMarine.class, new SpaceMarineDeserializer())
            .create();

    /**
     * метод сериализации коллекции в формат json
     */
    private String convertToJson(Message message, OutputManager outputManager) {
        try {
            String jsonElement = gson.toJson(message);
            return jsonElement.toString();
        } catch (Exception e) {
            outputManager.println("Ошибка сериализации");
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
    private PriorityQueue<SpaceMarine> convertToCollection(String s, OutputManager outputManager) {

        Type collType = new TypeToken<PriorityQueue<SpaceMarine>>() {
        }.getType();
        try {
            PriorityQueue<SpaceMarine> result = gson.fromJson(s, collType);

        return result;
        } catch (Exception e) {
            outputManager.println("Коллекция не валидна");
        }
                return null;
    }

//    /**
//     * запись коллекции в файл
//     */
///*@args
//        PriorityQueue<SpaceMarine> collection
//     */
//    public void writeCollection(PriorityQueue<SpaceMarine> collection) {
//        try {
//            var jsonElement = convertToJson(collection, outputManager);
//            if (jsonElement == null) return;
//            Writer writer = new FileWriter(System.getenv("CAPIPA"));
//            try {
//                writer.write(jsonElement);
//                writer.flush();
//                writer.close();
//                outputManager.println("Коллекция успешно сохранена в файл!");
//            } catch (FileNotFoundException | NullPointerException e) {
//                outputManager.println("Файл не найден ");
//            }
//        } catch (IOException e) {
//            outputManager.println("Ошибка записи в файл! ");
//        }
//    }
//
//    /**
//     * чтение коллекции из файла
//     */
//    /*
//     * @return - коллекция
//     */
//    public Collection<SpaceMarine> readCollection() {
//
//        String jsonPath = System.getenv("CAPIPA");
//        if (jsonPath == null || jsonPath.isEmpty())
//            jsonPath = "test.json";
//
//        try {
//            File jsonFile = new File(jsonPath);
//
//            if (!jsonFile.exists())
//                jsonFile.createNewFile();
//
//            if (!jsonFile.canRead()) {
//                System.out.println("Нет доступа к чтению! Чтение недоступно! ");
//                System.exit(1);
//            }
//            if (!jsonFile.canWrite()) {
//                System.out.println("Нет доступа к записи! Сохранение недоступно! ");
//                System.exit(1);
//
//            }
//        } catch (IOException e) {
//            System.out.println("Ошибка. Неправильно указан путь в переменной окружения!");
//            return new PriorityQueue<SpaceMarine>();
//        }
//
//        StringBuilder builder = new StringBuilder();
//
//        try (BufferedReader buffer = new BufferedReader(
//                new FileReader(jsonPath))) {
//
//            String str;
//
//            // Condition check via buffer.readLine() method
//            // holding true upto that the while loop runs
//            while ((str = buffer.readLine()) != null) {
//                builder.append(str).append("\n");
//            }
//
//            PriorityQueue<SpaceMarine> sm = convertToCollection(builder.toString(), outputManager);
//            if (sm != null) {
//                outputManager.println("Коллекция успешно загружена!");
//                return sm;
//            }
//        } catch (FileNotFoundException exception) {
//            outputManager.println("Загрузочный файл не найден!");
//        } catch (IllegalStateException exception) {
//            outputManager.println("Непредвиденная ошибка!");
//            System.exit(0);
//        } catch (IOException e) {
//            outputManager.println("Ошибка при чтении данных из файла ");
//        }
//        return new PriorityQueue<>();
//    }
}
