package Server.fileHandlers;

import Server.Server;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Класс, отвечающий за сохранение текущей коллекции в xml-файл
 */
public class GSONWriter {

    /**
     * Метод, сохраняющий данные в формате xml, ИСПОЛЬЗУЕТСЯ СТОРОННЯЯ БИБЛИОТЕКА XStream
     *
     * @param file файл, в который производится запись
     * @param spaceMarines коллекция, которую необходимо сохранить
     * @throws IOException возникает при невозможности записи в файл полученных данных
     */
    public static void writeOld(File file, CollectionManager spaceMarines) throws IOException {
        XStream xStream = new XStream();
        xStream.alias("dragon", SpaceMarine.class);
        xStream.alias("set", CollectionManager.class);
        xStream.addImplicitCollection(CollectionManager.class, "dragons");
        String xmlText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" + xStream.toXML(spaceMarines.getSpaceMarines());
        FileWriter writer = new FileWriter(file);
        writer.write(xmlText);
        writer.flush();
        writer.close();
    }
    public static void write(File file, CollectionManager spaceMarines) throws IOException {
        FileWriter fileWriter = new FileWriter(file, false);
        Gson gson = new Gson();
        fileWriter.write(gson.toJson(CollectionManager.spaceMarines));
        fileWriter.flush();
        System.out.println("\nКоллекция успешно сохранена");
    }
}
