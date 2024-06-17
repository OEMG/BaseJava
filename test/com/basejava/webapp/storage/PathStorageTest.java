package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialization.StreamSerialization;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new StreamSerialization()));
    }
}