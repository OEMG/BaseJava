package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume resume, int index) {
        storage[countResume] = resume;
    }

    @Override
    protected void doDelete(int index) {
        storage[index] = storage[countResume - 1];
    }
}
