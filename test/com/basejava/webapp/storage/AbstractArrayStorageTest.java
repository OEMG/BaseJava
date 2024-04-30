package com.basejava.webapp.storage.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.AbstractArrayStorage;
import com.basejava.webapp.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private static final String OVERFLOW_MESSAGE = "Overflow occurred ahead of time";

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void overflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.CAPACITY; i++) {
                storage.save(new Resume("test"));
            }
        } catch (StorageException e) {
            fail(OVERFLOW_MESSAGE);
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("test")));
    }
}