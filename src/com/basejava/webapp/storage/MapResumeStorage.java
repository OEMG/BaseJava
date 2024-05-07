package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void saveResume(Resume r, Resume resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected void deleteResume(Resume resume) {
        map.remove(resume.getUuid());
    }

    @Override
    protected void updateResume(Resume r, Resume resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume getResume(Resume resume) {
        return resume;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }
}
