package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    protected void insertResume(Resume r, int index) {

        storage[size] = r;
    }

    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    protected boolean isExist(Resume r) {
        for(int i = 0; i < size; i++) {
            if(storage[i].getUuid().equals((r.getUuid()))) {
                return true;
            }
        }
        return false;
    }
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
