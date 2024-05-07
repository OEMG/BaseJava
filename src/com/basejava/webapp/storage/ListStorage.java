package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<Resume> copyList() {
        return list;
    }

    @Override
    public void saveResume(Resume resume, Integer searchKey) {
        list.add(resume);
    }

    @Override
    public void deleteResume(Integer searchKey) {
        list.remove(searchKey.intValue());
    }

    @Override
    public void updateResume(Resume resume, Integer searchKey) {
        list.set(searchKey, resume);
    }

    @Override
    public boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public final Resume getResume(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            String listUuid = list.get(i).getUuid();
            if (listUuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
