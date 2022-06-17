package Server.fileHandlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Класс, отвечающий за стартовую обработку xml-файла с данными о коллекции
 */
public class GSONReader {

    public HashSet<SpaceMarine> readOLD(File file) throws IOException, NumberFormatException {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.alias("space marine", SpaceMarine.class);
        xStream.alias("set", CollectionManager.class);
        xStream.addImplicitCollection(CollectionManager.class, "marines");
        StringBuilder xmlText = new StringBuilder();
        FileReader reader = new FileReader(file);
        Scanner sc = new Scanner(reader);
        while (sc.hasNextLine()) {
            xmlText.append(sc.nextLine());
        }
        reader.close();
        CollectionManager manager = (CollectionManager) xStream.fromXML(String.valueOf(xmlText));
        if (!fileIsCorrect(manager)) {
            System.out.println("Ошибка чтения файла на уровне валидации");
            System.exit(0);
        }
        return manager.getSpaceMarines();
    }

    private boolean fileIsCorrect(CollectionManager manager) {
        for (SpaceMarine marine : manager.getSpaceMarines()) {
            if (marine == null) {
                return false;
            }
        }
        return true;
    }

    public HashSet<SpaceMarine> read(File file) throws IOException {
        InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader fileBuffer = new BufferedReader(fileReader);
        String jsonCollection = String.valueOf(fileBuffer.readLine());
        Type dataType = new TypeToken<HashSet<SpaceMarine>>() {}.getType();
        Gson gson = new Gson();
        HashSet<SpaceMarine> spaceMarines = new HashSet<>();
        spaceMarines = gson.fromJson(jsonCollection, dataType);
        CollectionManager manager = new CollectionManager();
        manager.addSpaceMarines(spaceMarines);
        return manager.getSpaceMarines();
    }
}
