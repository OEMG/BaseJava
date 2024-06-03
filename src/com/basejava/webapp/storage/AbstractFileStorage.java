package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (directory.canRead() && directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected Resume getResume(File file) {
        return doRead(file);
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> copyList() {
        List<Resume> list = new ArrayList<>();
        processFiles(file -> list.add(doRead(file)));
        return list;
    }

    @Override
    public void clear() {
        processFiles(File::delete);
    }

    @Override
    public int size() {
        return Objects.requireNonNull(new File(directory
                .toURI())
                .listFiles(), "directory must not be null")
                .length;
    }

    private void processFiles(Consumer<File> fileConsumer) {
        File[] files = Objects.requireNonNull(directory.listFiles());
        for (File file : files) {
            if (!file.isDirectory()) {
                fileConsumer.accept(file);
            }
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file);
}
