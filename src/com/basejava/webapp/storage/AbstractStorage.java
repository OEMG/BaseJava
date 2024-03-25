package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        saveResume(resume, index);
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (findIndex(uuid) < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(index);
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        updateResume(resume, index);
    }

    public abstract int findIndex(String uuid);

    public abstract void deleteResume(int index);

    public abstract Resume getResume(int index);

    public abstract void saveResume(Resume resume, int index);

    public abstract void updateResume(Resume resume, int index);

}
