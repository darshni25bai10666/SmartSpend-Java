package com.smartspend.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileExporter {
    private FileExporter() {
    }

    public static void exportReport(String report, String filePath)
            throws IOException {

        File file = new File(filePath);
        File parentFolder = file.getParentFile();

        if (parentFolder != null && !parentFolder.exists()) {
            parentFolder.mkdirs();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(report);
        writer.close();
    }
}