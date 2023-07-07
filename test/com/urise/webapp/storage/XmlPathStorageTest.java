package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.XmlResumeSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlResumeSerializer()));
    }
}
