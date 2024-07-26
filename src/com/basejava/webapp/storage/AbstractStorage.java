package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getExistingSearchKey(resume.getUuid());
        saveResume(resume, searchKey);
    }

    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getNotExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getNotExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> sortedList = copyList();
        Collections.sort(sortedList);
        return sortedList;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
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
