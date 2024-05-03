package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<Object, Resume> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public List<Resume> copyList() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        map.put(searchKey, resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        map.put(searchKey, resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return map.get(searchKey);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }
}
