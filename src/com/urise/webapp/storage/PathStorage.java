package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.ResumeSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final ResumeSerializer serializer;
    private final Path directory;

    protected PathStorage(File file, ResumeSerializer serializer) {
        this.serializer = serializer;
        directory = Paths.get(file.getAbsolutePath());
        Objects.requireNonNull(directory, "Directory mustn't be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(file.getAbsolutePath() + " is not directory or is not writable");
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path Path) {
        return Files.exists(Path);
    }

    @Override
    protected Resume doGet(Path Path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", Path.toAbsolutePath() + Path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path Path) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume r, Path Path) {
        try {
            Files.createFile(Path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path ", Path.getFileName().toString(), e);
        }
        doUpdate(r, Path);
    }

    @Override
    protected void doDelete(Path Path) {
        try {
            Files.deleteIfExists(Path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        getFilesList().forEach(path -> list.add(doGet(path)));
        return list;
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doWrite(Resume r, OutputStream os) throws IOException {
        serializer.doWrite(r, os);
    }

    protected Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
