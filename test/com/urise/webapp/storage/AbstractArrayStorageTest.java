package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{


    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        STORAGE.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                Resume r = new Resume("Name");
                STORAGE.save(r);
            }
        } catch (StorageException e) {
            Assert.fail("Ошибка переполнения вызвана раньше, чем планировалось");
        }
        Resume r = new Resume("Name");
        STORAGE.save(r);
    }
}