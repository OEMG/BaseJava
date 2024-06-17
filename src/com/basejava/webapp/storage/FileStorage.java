package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialization.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    private final SerializationStrategy strategy;

    protected FileStorage(File directory, SerializationStrategy strategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.strategy = strategy;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() && directory.canWrite()) {
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
        if (!file.delete()) {
            throw new StorageException("Error: File has not been deleted", file.getName());
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return strategy.doRead(new FileInputStream(file));
        } catch (IOException e) {
            throw new StorageException("Error: File has not been read", file.getName(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Error: File has not been saved", file.getName(), e);
        }
        updateResume(resume, file);
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error: File has not been write", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> copyList() {
        List<Resume> list = new ArrayList<>();
        processFiles(file -> list.add(getResume(file)));
        return list;
    }

    @Override
    public void clear() {
        processFiles(this::deleteResume);
    }

    @Override
    public int size() {
        return getCheckedListFiles().length;
    }

    private void processFiles(Consumer<File> fileConsumer) {
        File[] files = getCheckedListFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                fileConsumer.accept(file);
            }
        }
    }

    private File[] getCheckedListFiles() {
        if (directory == null) {
            throw new StorageException("Error: directory must not be null");
        }
        return directory.listFiles();
    }
}
