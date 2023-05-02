package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_4));
    }
    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(4, storage.size());
        storage.get(UUID_4);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void update() throws Exception {
        Resume resumeUpdate = new Resume(UUID_1);
        storage.update(resumeUpdate);
        Assert.assertTrue(resumeUpdate == storage.get(UUID_1));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumeAll = storage.getAll();
        Assert.assertTrue(isContain(resumeAll, RESUME_1));
        Assert.assertTrue(isContain(resumeAll, RESUME_2));
        Assert.assertTrue(isContain(resumeAll, RESUME_3));
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = 3; i < 10000; i++) {
                Resume r = new Resume();
                storage.save(r);
            }
        } catch (StorageException e) {
            Assert.fail("Ошибка переполнения вызвана раньше, чем планировалось");
        }
        Resume r = new Resume();
        storage.save(r);
    }


    public boolean isContain(Resume[] resumeArr, Resume resume) {
        for (Resume r : resumeArr) {
            if (r.equals(resume)) {
                return true;
            }
        }
        return false;
    }
}