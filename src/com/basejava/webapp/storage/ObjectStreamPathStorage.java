package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

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

public class ObjectStreamPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    SerializationStrategy ss;

    protected ObjectStreamPathStorage(String dir, SerializationStrategy ss) {
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
            updateResume(resume, path);
        } catch (IOException e) {
            throw new StorageException("Error saving resume", path.getFileName().toString(), e);
        }
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
        try (Stream<Path> stream = Files.list(directory)) {
            stream.forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Error deleting files in directory: " + directory);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> stream = Files.list(directory)) {
            return (int) stream.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}
