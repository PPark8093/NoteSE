package io.github.ppark;

import java.io.*;
import java.util.*;

public class ReadNoteList {

    String path = System.getProperty("user.home") + File.separator + "NoteSE";
    File dir = new File(path);

    Map<String, String> lists;

    public Map<String, String> readList() {
        if (!(dir.exists())) {
            dir.mkdirs();
            readList();
        } else {
            lists = new HashMap<>();
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(".txt")) {
                    lists.put(files[i].getName(), String.valueOf(files[i]));
                }
            }
            return lists;
        }

        return null;
    }
}
