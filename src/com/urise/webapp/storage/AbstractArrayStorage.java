package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }
    public int size() {
        return size;
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполненно");
        }
        if (index > 0) {
            printErrorResumeNotFound(r.getUuid());
        } else {
            insertResume(r, index);
            size++;
        }
    }

    protected abstract void insertResume(Resume r, int index);
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            printErrorResumeNotFound(uuid);
            return null;
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            printErrorResumeNotFound(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            size--;
        } else {
            printErrorResumeNotFound(uuid);
        }
    }

    protected abstract void deleteResume(int index);
    protected abstract int findIndex(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
    protected void printErrorResumeNotFound(String uuid) {
        System.out.printf("Резюме с uuid %s не найдено%n", uuid);
    }

}
