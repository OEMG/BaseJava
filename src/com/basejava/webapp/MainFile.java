package com.basejava.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File directory = new File(".\\");
        printFiles(directory);
    }

    private static void printFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printFiles(file);
                } else {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }
}
