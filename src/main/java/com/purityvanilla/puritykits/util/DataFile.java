package com.purityvanilla.puritykits.util;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataFile {

    private String dataFilePath;
    private File dataFile;
    private Type mapType;

    public DataFile(String path, Type mapType) {
        this.dataFilePath = path;
        this.dataFile = new File(dataFilePath);
        this.mapType = mapType;

        dataFile.getParentFile().mkdirs();

        try {
            dataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T, U> void save(HashMap<T ,U> map) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        if (map != null) {
            String jsonData = gson.toJson(map);

            try {
                Files.write(jsonData.getBytes(), dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T, U> HashMap<T, U> load(){
        Gson gson = new Gson();
        Reader reader = null;

        try {
            reader = new FileReader(dataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HashMap<T, U> loadedMap = gson.fromJson(reader, mapType);
        if (loadedMap != null) {
            return loadedMap;
        }
        return new HashMap<T, U>();
    }

}