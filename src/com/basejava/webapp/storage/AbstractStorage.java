package main.java.com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    public final void save(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        saveResume(resume, searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    public final Resume get(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public final void update(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void saveResume(Resume resume, Object searchKey);

    protected abstract void updateResume(Resume resume, Object searchKey);

    protected abstract boolean isExist(Object searchKey);
}
