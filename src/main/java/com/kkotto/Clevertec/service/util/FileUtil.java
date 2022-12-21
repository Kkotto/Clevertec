package com.kkotto.Clevertec.service.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.kkotto.Clevertec.service.util.consts.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtil {
    private static FileUtil instance;
    static final Logger logger = LogManager.getLogger(FileUtil.class);

    private FileUtil() {
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
        String path;
        if (filePath.contains(Constants.DIRECTORY_REGEX)) {
            path = filePath.substring(0, filePath.lastIndexOf(Constants.DIRECTORY_REGEX));
        } else {
            path = filePath;
        }
        if (path.contains(Constants.DIRECTORY_REGEX)) {
            createPathDirectories(path);
        } else {
            createDirectory(path);
        }
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

    public void createPathDirectories(String filePath) {
        String[] directories = filePath.split(Constants.DIRECTORY_REGEX);
        StringBuilder currentPath = new StringBuilder();
        for (String directory : directories) {
            currentPath.append(directory);
            createDirectory(currentPath.toString());
        }
    }

    public void createDirectory(String directoryPath) {
        File currentDirectory = new File(directoryPath);
        if (!currentDirectory.exists()) {
            currentDirectory.mkdirs();
        }
    }

    public boolean isFileCSV(String fileName) {
        return fileName.matches(Constants.CSV_FILENAME_REGEX);
    }
}
