package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }

    @Override
    protected void saveOperation(Resume resume, int index) {
        int newIndex = -index - 1;
        if (newIndex < countResume && countResume > 0) {
            Resume[] shiftingPart = Arrays.copyOfRange(storage, newIndex, countResume);
            System.arraycopy(shiftingPart, 0, storage, newIndex + 1, shiftingPart.length);
        }
        storage[-index - 1] = resume;
        countResume++;
    }

    @Override
    protected void deleteOperation(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResume - index);
        countResume--;
    }
}
