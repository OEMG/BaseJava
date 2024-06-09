package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
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
            return doRead(new FileInputStream(file));
        } catch (IOException e) {
            throw new StorageException("Error: File has not been read", file.getName(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            updateResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("Error: File has not been saved", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}
