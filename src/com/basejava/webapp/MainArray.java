package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SortedArrayStorage;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.storage.ArrayStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainArray {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();
    static String uuid = null;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Введите одну из команд - " +
                    "(list | size | save uuid | delete uuid | get uuid | update uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list" -> printAll();
                case "size" -> System.out.println(ARRAY_STORAGE.size());
                case "save" -> saveResume();
                case "delete" -> deleteResume();
                case "get" -> System.out.println(ARRAY_STORAGE.get(uuid));
                case "update" -> updateResume();
                case "clear" -> clearStorage();
                case "exit" -> {
                    return;
                }
                default -> System.out.println("Неверная команда.");
            }
        }
    }

    private static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }

    private static void saveResume() {
        Resume resume = new Resume(uuid);
        ARRAY_STORAGE.save(resume);
        printAll();
    }

    private static void deleteResume() {
        ARRAY_STORAGE.delete(uuid);
        printAll();
    }

    private static void updateResume() {
        Resume resume = new Resume(uuid);
        ARRAY_STORAGE.update(resume);
        printAll();
    }

    private static void clearStorage() {
        ARRAY_STORAGE.clear();
        printAll();
    }
}
