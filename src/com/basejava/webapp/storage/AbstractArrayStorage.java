package main.java.com.basejava.webapp.storage;

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

    public List<Resume> getAllSorted() {
        Resume[] storageCopy = Arrays.copyOf(storage,countResume);
        List<Resume> sortedStorage = new ArrayList<>(Arrays.asList(storageCopy));
        sortedStorage.sort(RESUME_COMPARATOR);
        return sortedStorage;
    }

    public void saveResume(Resume resume, Object searchKey) {
        if (countResume >= CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        doSave(resume, (Integer) searchKey);
        countResume++;
    }

    public void deleteResume(Object searchKey) {
        doDelete((Integer) searchKey);
        storage[countResume--] = null;
    }

    public void updateResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    public final Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    public boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doDelete(int index);
}
