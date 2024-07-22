package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        return perform("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString(1).trim(), rs.getString(2)));
            }
            return list;
        });
    }

    @Override
    public int size() {
        return perform("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
    }

    @Override
    public void save(Resume resume) {
        checkExist(resume.getUuid());
        execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
        });
    }

    @Override
    public void delete(String uuid) {
        checkNotExist(uuid);
        execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.executeUpdate();
        });
    }

    @Override
    public Resume get(String uuid) {
        return perform("SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume resume) {
        checkNotExist(resume.getUuid());
        execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.executeUpdate();
        });
    }

    @FunctionalInterface
    private interface SqlExecutor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    private void execute(String sqlCommand, SqlExecutor executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    private interface SqlFunction<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    private <T> T perform(String sqlCommand, SqlFunction<T> function) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return function.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private void checkNotExist(String uuid) {
        execute("SELECT 1 FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
            }
        });
    }

    private void checkExist(String uuid) {
        execute("SELECT 1 FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    throw new ExistStorageException(uuid);
                }
            }
        });
    }
}
