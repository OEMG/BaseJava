package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    List<Resume> getAllSorted();

    int size();

    void save(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    void update(Resume resume);

}
