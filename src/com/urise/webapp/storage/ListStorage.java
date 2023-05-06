package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected int findIndex(String uuid) {
        int index = 0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean isExist(Resume r) {
        return storage.contains(r);
    }

    @Override
    protected void storageSave(Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume storageGet(int index) {
        return storage.get(index);
    }

    @Override
    protected void storageUpdate(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected void storageDelete(int index) {
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
