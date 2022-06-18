package Server.fileHandlers;

import com.google.gson.Gson;
import Common.entities.CollectionManager;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class GSONWriter {

    public static void write(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file, false);
        Gson gson = new Gson();
        fileWriter.write(gson.toJson(CollectionManager.spaceMarines));
        fileWriter.flush();
        System.out.println("\nКоллекция успешно сохранена");
    }
}
