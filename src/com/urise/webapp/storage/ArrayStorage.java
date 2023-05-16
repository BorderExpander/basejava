package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    protected void insertResume(Resume r, Integer index) {
        storage[size] = r;
    }

    protected void deleteResume(Integer index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
