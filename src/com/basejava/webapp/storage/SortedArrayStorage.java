package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage{

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    public Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "test");
        return Arrays.binarySearch(storage, 0, countResume, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void doSave(Resume resume, int index) {
        int newIndex = -index - 1;
        if (newIndex < countResume && countResume > 0) {
            System.arraycopy(storage, newIndex, storage, newIndex + 1, countResume - newIndex);
        }
        storage[newIndex] = resume;
    }

    @Override
    protected void doDelete(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResume - index);
    }
}
