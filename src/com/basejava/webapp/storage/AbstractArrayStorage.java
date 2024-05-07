package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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

    protected void saveResume(Resume resume, Integer searchKey) {
        if (countResume >= CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        doSave(resume, searchKey);
        countResume++;
    }

    protected void deleteResume(Integer searchKey) {
        doDelete(searchKey);
        storage[countResume--] = null;
    }

    protected void updateResume(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    protected final Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doDelete(int index);
}
