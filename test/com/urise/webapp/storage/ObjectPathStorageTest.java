package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectResumeSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectResumeSerializer()) {
        });
    }
}
