package finalproject.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FileTransfer {

    private static final String FILE_NAME = "dataFile.json";

    private static final Type CLIENT_MAP_TYPE = new TypeToken<Map<String, Client>>() {}.getType();

    // ISO-8601 формат для LocalDateTime. [web:139]
    private static final DateTimeFormatter LDT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static final JsonSerializer<LocalDateTime> LOCAL_DATE_TIME_SERIALIZER =
            (src, typeOfSrc, context) -> new JsonPrimitive(src.format(LDT_FORMATTER));

    private static final JsonDeserializer<LocalDateTime> LOCAL_DATE_TIME_DESERIALIZER =
            (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), LDT_FORMATTER);

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_SERIALIZER)
            .registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_DESERIALIZER)
            .setPrettyPrinting()
            .create();

    /**
     * Кросс-платформенный путь:
     * {user.home}/finalhomeproject/dataFile.json
     */
    private static Path getStoragePath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, "finalhomeproject", FILE_NAME);
    }

    public static Map<String, Client> loadClients() {
        Path path = getStoragePath();

        if (Files.notExists(path)) {
            return new HashMap<>();
        }

        try {
            String json = Files.readString(path, StandardCharsets.UTF_8).trim();
            if (json.isEmpty() || json.equals("{}")) {
                return new HashMap<>();
            }

            Map<String, Client> map = GSON.fromJson(json, CLIENT_MAP_TYPE);
            return (map != null) ? map : new HashMap<>();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла данных: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public static void saveClients(Map<String, Client> clients) {
        if (clients == null) {
            clients = new HashMap<>();
        }

        Path path = getStoragePath();

        try {
            Path parent = path.getParent();
            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }

            String json = GSON.toJson(clients, CLIENT_MAP_TYPE);
            Files.writeString(path, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла данных: " + e.getMessage());
        }
    }
}
