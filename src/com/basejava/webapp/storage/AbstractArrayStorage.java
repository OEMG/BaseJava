package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int CAPACITY = 10000;
    public final Resume[] storage = new Resume[CAPACITY];
    protected int countResume;

    public int size() {
        return countResume;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public void saveResume(Resume resume, int index) {
        if (countResume >= CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        save(resume, index);
        countResume++;
    }

    public void deleteResume(int index) {
        delete(index);
        storage[countResume--] = null;
    }

    public void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    public final Resume getResume(int index) {
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    //Под вопросом названия методов. Они аналогичны AbstractStorage
    protected abstract void save(Resume resume, int index);

    protected abstract void delete(int index);
}
