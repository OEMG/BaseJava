package com.basejava.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File directory = new File("./src/com/basejava/webapp/");
        printFiles(directory);
    }

    private static void printFiles(File directory) {
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                System.out.println("   " + file.getName());
            }
        }
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(file.getName());
                printFiles(file);
            }
        }
    }
}