package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void size() {
        storage.size();
        assertEquals(3, storage.size());
    }

    @Test
    void getAll() {
        Resume[] expected = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Resume[] actual = storage.getAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    void save() {
        String uuid = "uuid4";
        Resume expected = new Resume(uuid);
        storage.save(expected);
        Resume actual = storage.get(uuid);
        assertEquals(expected, actual);

    }

    @Test
    void delete() {
        int expectedSize = storage.size() - 1;
        storage.delete(UUID_1);
        int actualSize = storage.size();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Resume[] storageCopy = storage.getAll();
            Resume actual = storageCopy[expectedSize];
        });
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void update() {
        Resume expected = new Resume(UUID_1);
        Resume[] storageCopy = storage.getAll();
        storageCopy[0] = expected;
        Resume actual = storage.get(UUID_1);
        assertEquals(expected, actual);

    }

    @Test
    void get() {
        Resume expected = new Resume(UUID_1);
        Resume actual = storage.get(UUID_1);
        assertEquals(expected, actual);
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void getExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("test"));
    }

    @Test
    void overflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.CAPACITY; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Overflow occurred ahead of time");
        }

    }
}