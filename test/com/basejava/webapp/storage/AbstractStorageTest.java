package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected final Storage storage;
    protected static final ResumeTestData TEST_DATA = new ResumeTestData();
    protected static final String UUID_1 = String.valueOf(UUID.randomUUID());
    protected static final String UUID_2 = String.valueOf(UUID.randomUUID());
    protected static final String UUID_3 = String.valueOf(UUID.randomUUID());
    protected static final String UUID_4 = String.valueOf(UUID.randomUUID());
    protected static final String TEST_UUID = String.valueOf(UUID.randomUUID());
    protected static final String FULL_NAME = "fullName";
    protected static final Resume RESUME_1 = TEST_DATA.initSections(UUID_1, FULL_NAME);
    protected static final Resume RESUME_2 = TEST_DATA.initSections(UUID_2, FULL_NAME);
    protected static final Resume RESUME_3 = TEST_DATA.initSections(UUID_3, FULL_NAME);
    protected static final Resume RESUME_4 = TEST_DATA.initSections(UUID_4, FULL_NAME);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(expected);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(RESUME_4.getUuid()));
    }

    @Test
    void update() {
        Resume expected = TEST_DATA.initSections(UUID_1, FULL_NAME);
        storage.update(expected);
        assertEquals(expected, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
    }

    @Test
    void get() {
        assertGet(RESUME_1, RESUME_2, RESUME_3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(TEST_UUID));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertEquals(new ArrayList<>(), storage.getAllSorted());
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    protected void assertGet(Resume... resume) {
        for (Resume value : resume) {
            assertEquals(value, storage.get(value.getUuid()));
        }
    }
}
