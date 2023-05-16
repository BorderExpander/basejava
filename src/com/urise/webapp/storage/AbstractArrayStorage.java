package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполненно", r.getUuid());
        } else {
            insertResume(r, (Integer)searchKey);
            size++;
        }
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        storage[searchKey] = r;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        deleteResume(searchKey);
        size--;
    }

//    public Resume[] getAll() {
//        return Arrays.copyOf(storage, size);
//    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    public int size() {
        return size;
    }

    protected abstract void insertResume(Resume r, Integer index);

    protected abstract void deleteResume(Integer index);

    protected abstract Integer findSearchKey(String uuid);

}
