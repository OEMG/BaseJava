package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialization.SerializationStrategy;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.nio.file.Files.isRegularFile;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private final SerializationStrategy ss;

    protected PathStorage(String dir, SerializationStrategy ss) {
        Objects.requireNonNull(dir, "directory must not be null");
        this.ss = ss;
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Error: Path has not been deleted", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return ss.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error: Path has not been read", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Error saving resume", path.getFileName().toString(), e);
        }
        updateResume(resume, path);
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            ss.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error: Path has not been write", path.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return isRegularFile(path);
    }

    @Override
    protected List<Resume> copyList() {
        List<Resume> list = new ArrayList<>();
        processPaths(path -> list.add(getResume(path)));
        return list;
    }

    @Override
    public void clear() {
        getListFiles().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getListFiles().count();
    }

    private void processPaths(Consumer<Path> pathConsumer) {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(directory)) {
            paths.forEach(path -> {
                if (!Files.isDirectory(path)) {
                    pathConsumer.accept(path);
                }
            });
        } catch (IOException e) {
            throw new StorageException("Error: Path has not been write", directory.getFileName().toString(), e);
        }
    }

    private Stream<Path> getListFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Error listing files in directory: " + directory, e);
        }
    }
}
