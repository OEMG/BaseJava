package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public interface Storage {
    Resume[] getAll();

    int size();

    void save(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    void update(Resume resume);

    void clear();
}
