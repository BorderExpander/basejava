package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectResumeSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectResumeSerializer()));
    }
}
