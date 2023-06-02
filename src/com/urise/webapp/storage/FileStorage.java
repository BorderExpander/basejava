package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.AbstractResumeSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final AbstractResumeSerializer serializer;

    protected FileStorage(File directory, AbstractResumeSerializer serializer) {
        this.serializer = serializer;
        Objects.requireNonNull(directory, "directory mustn't be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not read/write directory");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = getListFiles();
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = getListFiles();
        return files.length;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getAbsolutePath(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error", file.getName(), null);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = getListFiles();
        List<Resume> resumeList = new ArrayList<>();
        for (File file : files) {
            Resume r = doGet(file);
            resumeList.add(r);
        }
        return resumeList;
    }

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", null);
        }
        return files;
    }

    protected void doWrite(Resume r, OutputStream os) throws IOException {
        serializer.doWrite(r, os);
    }
    protected Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
