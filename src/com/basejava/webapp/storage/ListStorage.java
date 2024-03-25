package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    public List<Resume> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        list.add(resume);
    }

    @Override
    public void deleteResume(int index) {
        list.remove(index);
    }

    @Override
    public void updateResume(Resume resume, int index) {
        list.set(index, resume);
    }

    @Override
    public final Resume getResume(int index) {
        return list.get(index);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int findIndex(String uuid) {
        for (Resume resume : list) {
            if (resume.getUuid().equals(uuid)) {
                return list.indexOf(resume);
            }
        }
        return -1;
    }
}
