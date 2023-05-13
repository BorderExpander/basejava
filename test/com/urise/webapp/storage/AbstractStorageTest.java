package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AbstractStorageTest {
    protected final Storage STORAGE;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String NAME_1 = "name1";
    private static final String NAME_2 = "name2";
    private static final String NAME_3 = "name3";
    private static final String NAME_4 = "name4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = new Resume(UUID_1,NAME_1);
    private static final Resume RESUME_2 = new Resume(UUID_2,NAME_2);
    private static final Resume RESUME_3 = new Resume(UUID_3,NAME_3);
    private static final Resume RESUME_4 = new Resume(UUID_4,NAME_4);


    public AbstractStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    @Before
    public void setUp() {
        STORAGE.clear();
        STORAGE.save(RESUME_1);
        STORAGE.save(RESUME_2);
        STORAGE.save(RESUME_3);
    }

    @Test
    public void clear() {
        STORAGE.clear();
        assertSize(0);
        List<Resume> allResume = STORAGE.getAllSorted();
        Assert.assertEquals(0, allResume.size());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void save() {
        STORAGE.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {

        STORAGE.save(new Resume(UUID_1, NAME_1));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        STORAGE.get(UUID_NOT_EXIST);
    }

    @Test
    public void update() {
        String newName = "newName";
        Resume resumeUpdate = new Resume(UUID_1,newName);
        STORAGE.update(resumeUpdate);
        Assert.assertSame(resumeUpdate, STORAGE.get(UUID_1));
        Assert.assertEquals(newName,STORAGE.get(UUID_1).getFullName());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resumeUpdate = new Resume(UUID_NOT_EXIST, "Name");
        STORAGE.update(resumeUpdate);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        STORAGE.delete(UUID_1);
        assertSize(2);
        STORAGE.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        STORAGE.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(expected, STORAGE.getAllSorted());
        assertSize(3);
    }

    private void assertSize(int size) {
        Assert.assertEquals(STORAGE.size(), size);
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(STORAGE.get(r.getUuid()), r);
    }
}