package com.danzki.atm;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoaderJackson {
    private static String path;

    public LoaderJackson(String path) {
        this.path = path;
    }

    public static List<Cell> loadCells() {
        var dir = new File(path);
        List<Cell> cells = new ArrayList<>();
        var mapper = new ObjectMapper();
        if (dir != null) {
            for (File file : dir.listFiles()) {
                try {
                    Cell cell = mapper.readValue(file, Cell.class);
                    cells.add(cell);
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cells;
    }
}
