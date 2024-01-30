package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayStorage {
    private static final int CAPACITY = 10000;
    private final Resume[] storage = new Resume[CAPACITY];
    private int countResume;

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public int size() {
        return countResume;
    }

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (countResume >= CAPACITY) {
            System.out.println("Ошибка! Невозможно добавить резюме " + uuid + ". Хранилище переполнено.");
            return;
        }
        int index = findIndexByUuid(uuid);
        if (index != -1) {
            System.out.println("Ошибка! Резюме с Uuid: " + uuid + " уже есть в хранилище.");
            return;
        }
        storage[countResume++] = resume;
    }

    public void update(String uuid) {
        int index = findIndexByUuid(uuid);
        if (index != -1) {
            System.out.print("Для " + uuid + " введите новый uuid: ");
            String newUuid = new Scanner(System.in).next();
            storage[index].setUuid(newUuid);
            return;
        }
        System.out.println("Ошибка! Резюме " + uuid + " не найдено в хранилище.");
    }

    public void delete(String uuid) {
        int index = findIndexByUuid(uuid);
        if (index != -1) {
            countResume--;
            System.arraycopy(storage, index + 1, storage, index, countResume - index);
            storage[countResume] = null;
            return;
        }
        System.out.println("Ошибка! Резюме " + uuid + " не найдено в хранилище.");
    }

    public String get(String uuid) {
        int index = findIndexByUuid(uuid);
        if (index != -1) {
            return uuid;
        }
        return "Ошибка! Резюме " + uuid + " не найдено в хранилище.";
    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    private int findIndexByUuid(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
