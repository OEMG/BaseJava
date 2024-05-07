package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    public final void save(Resume resume) {
        SK searchKey = getExistingSearchKey(resume.getUuid());
        saveResume(resume, searchKey);
    }

    public final void delete(String uuid) {
        SK searchKey = getNotExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    public final Resume get(String uuid) {
        SK searchKey = getNotExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public final void update(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> sortedList = copyList();
        sortedList.sort(RESUME_COMPARATOR);
        return sortedList;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract SK getSearchKey(String uuid);

    protected abstract void deleteResume(SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract void updateResume(Resume resume, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> copyList();
}
