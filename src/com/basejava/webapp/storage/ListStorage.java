package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage{
    private final List<Resume> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<Resume> getAllSorted() {
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        list.add(resume);
    }

    @Override
    public void deleteResume(Object searchKey) {
        list.remove(((Integer) searchKey).intValue());
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    public boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    public final Resume getResume(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Object getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            String listUuid = list.get(i).getUuid();
            if (listUuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
