package Server.fileHandlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;

public class GSONReader {

    public void read(File file) throws IOException {
        InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader fileBuffer = new BufferedReader(fileReader);
        String jsonCollection = String.valueOf(fileBuffer.readLine());
        Type dataType = new TypeToken<HashSet<SpaceMarine>>() {
        }.getType();
        Gson gson = new Gson();
        HashSet<SpaceMarine> spaceMarines = gson.fromJson(jsonCollection, dataType);
        Iterator<SpaceMarine> iterator = spaceMarines.iterator();
        while (iterator.hasNext()) {
            SpaceMarine i = iterator.next();
            i.setId();
            if (i.getCreationDate() == null) i.setCreationDate();
            CollectionManager.spaceMarines.add(i);
        }
    }
}

