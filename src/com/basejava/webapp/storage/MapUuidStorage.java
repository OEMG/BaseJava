package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public List<Resume> copyList() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected void saveResume(Resume resume, String uuid) {
        map.put(uuid, resume);
    }

    @Override
    protected void deleteResume(String uuid) {
        map.remove(uuid);
    }

    @Override
    protected void updateResume(Resume resume, String uuid) {
        map.put(uuid, resume);
    }

    @Override
    protected boolean isExist(String uuid) {
        return map.containsKey(uuid);
    }

    @Override
    protected Resume getResume(String uuid) {
        return map.get(uuid);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }
}
