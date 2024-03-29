package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }

    @Override
    protected void save(Resume resume, int index) {
        int newIndex = -index - 1;
        if (newIndex < countResume && countResume > 0) {
            System.arraycopy(storage, newIndex, storage, newIndex + 1, countResume - newIndex);
        }
        storage[-index - 1] = resume;
    }

    @Override
    protected void delete(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResume - index);
    }
}
