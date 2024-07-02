package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements SerializationStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeContacts(resume, dos);
            writeSections(resume, dos);
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        writeCollection(dos, resume.getContacts().entrySet(), contact -> {
            dos.writeUTF(contact.getKey().name());
            dos.writeUTF(contact.getValue());
        });
    }

    private void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        writeCollection(dos, resume.getSections().entrySet(), entry -> {
            SectionType sectionType = entry.getKey();
            AbstractSection section = entry.getValue();
            dos.writeUTF(sectionType.name());
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getDescription());
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> list = ((ListSection) section).getList();
                    writeCollection(dos, list, dos::writeUTF);
                }
                case EXPERIENCE, EDUCATION ->
                        writeCollection(dos, ((CompanySection) section).getCompanies(), company -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite() != null ? company.getWebsite() : "");
                            writeCollection(dos, company.getPeriods(), period -> {
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription() != null ? period.getDescription() : "");
                                dos.writeUTF(String.valueOf(period.getStartDate()));
                                dos.writeUTF(String.valueOf(period.getEndDate()));
                            });
                        });
            }
        });
    }

    @FunctionalInterface
    private interface DataWriter<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, DataWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readEntries(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readEntries(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSections(dis, sectionType));
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface DataHandler {
        void handle() throws IOException;
    }

    private void readEntries(DataInputStream dis, DataHandler handler) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            handler.handle();
        }
    }

    @FunctionalInterface
    private interface DataReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, DataReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private AbstractSection readSections(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE, PERSONAL -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListSection(readList(dis, dis::readUTF));
            }
            case EXPERIENCE, EDUCATION -> {
                return new CompanySection(
                        readList(dis, () -> new Company(
                                dis.readUTF(),
                                dis.readUTF(),
                                readList(dis, () -> new Period(
                                        dis.readUTF(),
                                        dis.readUTF(),
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()))
                                ))));
            }
        }
        return new AbstractSection() {};
    }
}