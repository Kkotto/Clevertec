package com.kkotto.Clevertec.service.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtil {
    private static FileUtil instance;
    static final Logger logger = LogManager.getLogger(FileUtil.class);

    private FileUtil() {
        File receiptsDirectory = new File("Receipts");
        if (!receiptsDirectory.exists()) {
            receiptsDirectory.mkdirs();
        }
    }

    public static FileUtil getInstance() {
        if (instance == null) {
            instance = new FileUtil();
        }
        return instance;
    }

    public void writeToFile(File file, List<String> lines) {
        if (!file.exists()) {
            file = createFile(file.getPath());
        }
        try (FileWriter writer = new FileWriter(file, false)) {
            for (String line : lines) {
                writer.write(line);
            }
        } catch (IOException e) {
            logger.error("Impossible to write to file " + file.getName());
        }
    }

    public File createFile(String filePath) {
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                logger.info(file.getName() + " was successfully created.");
            } else {
                logger.info(file.getName() + " already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Impossible to create file " + file.getName());
        }
        return file;
    }
}
