package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.*;
import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1; // = new Resume(UUID_1);
    private static final Resume RESUME_2; // = new Resume(UUID_2);
    private static final Resume RESUME_3; // = new Resume(UUID_3);
    private static final Resume RESUME_4; // = new Resume(UUID_4);

    static {
        RESUME_1 = new Resume(UUID_1);
        RESUME_2 = new Resume(UUID_2);
        RESUME_3 = new Resume(UUID_3);
        RESUME_4 = new Resume(UUID_4);
    }

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        Storage emptyStorage = new ArrayStorage();
        assertArrayEquals(emptyStorage.getAll(), storage.getAll());
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void alreadyExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void update() throws Exception {
        Resume resumeUpdate = new Resume(UUID_1);
        storage.update(resumeUpdate);
        Assert.assertSame(resumeUpdate, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resumeUpdate = new Resume(UUID_NOT_EXIST);
        storage.update(resumeUpdate);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, storage.getAll());
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                Resume r = new Resume();
                storage.save(r);
            }
        } catch (StorageException e) {
            Assert.fail("Ошибка переполнения вызвана раньше, чем планировалось");
        }
        Resume r = new Resume();
        storage.save(r);
    }

    @Test(expected = ExistStorageException.class)
        public void saveExist() {
            storage.save(new Resume(UUID_1));
        }

    public void assertSize(int size) {
        Assert.assertEquals(storage.size(), size);
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(storage.get(r.getUuid()), r);
    }
}