package com.urise.webapp.storage;


import com.urise.webapp.storage.strategy.JsonResumeSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonResumeSerializer()));
    }
}
