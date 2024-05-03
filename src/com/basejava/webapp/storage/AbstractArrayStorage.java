package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int CAPACITY = 10000;
    public final Resume[] storage = new Resume[CAPACITY];
    protected int countResume;

    public int size() {
        return countResume;
    }

    @Override
    public List<Resume> copyList() {
        Resume[] storageCopy = Arrays.copyOf(storage,countResume);
        return new ArrayList<>(Arrays.asList(storageCopy));
    }

    protected void saveResume(Resume resume, Object searchKey) {
        if (countResume >= CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        doSave(resume, (Integer) searchKey);
        countResume++;
    }

    protected void deleteResume(Object searchKey) {
        doDelete((Integer) searchKey);
        storage[countResume--] = null;
    }

    protected void updateResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    protected final Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doDelete(int index);
}
