package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10000;
    protected final Resume[] storage = new Resume[CAPACITY];
    protected int countResume;

    public int size() {
        return countResume;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        if (countResume >= CAPACITY) {
            System.out.println("Ошибка! Невозможно добавить резюме " + uuid + ". Хранилище переполнено.");
            return;
        }
        int index = findIndex(uuid);
        if (index >= 0) {
            System.out.println("Ошибка! Резюме с Uuid: " + uuid + " уже есть в хранилище.");
            return;
        }
        saveResume(resume, index);
        countResume++;
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Ошибка! Резюме " + uuid + " не найдено в хранилище.");
            return;
        }
        deleteResume(index);
        storage[countResume--] = null;
    }

    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Ошибка! Резюме " + uuid + " не найдено в хранилище.");
            return;
        }
        storage[index] = resume;
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Ошибка! Резюме " + uuid + " не найдено в хранилище.");
            return null;
        }
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

}
