package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public Object getSearchKey(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void save(Resume resume, int index) {
        storage[countResume] = resume;
    }

    @Override
    protected void delete(int index) {
        storage[index] = storage[countResume - 1];
    }
}
