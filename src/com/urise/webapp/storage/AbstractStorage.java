package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storageUpdate(index, r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (isExist(r)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storageSave(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return storageGet(index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storageDelete(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public int size() {
        return 0;
    }

    protected abstract int findIndex(String uuid);

    protected abstract boolean isExist(Resume r);

    protected abstract Resume storageGet(int index);

    protected abstract void storageUpdate(int index, Resume r);

    protected abstract void storageSave(Resume r);

    protected abstract void storageDelete(int index);
}
