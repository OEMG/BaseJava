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

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(resume, dis);
            readSections(resume, dis);
            return resume;
        }
    }

    private static void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        writeCollection(dos, resume.getContacts().entrySet(), entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    private static void writeSections(Resume resume, DataOutputStream dos) throws IOException {
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
                case EXPERIENCE, EDUCATION -> writeCollection(dos, ((CompanySection) section).getCompanies(), company -> {
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
    interface ThrowingConsumer<T> {
        void accept(T t) throws IOException;
    }

    private static <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ThrowingConsumer<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.accept(t);
        }
    }

    private static void readContacts(Resume resume, DataInputStream dis) throws IOException {
        int contactsSize = dis.readInt();
        for (int i = 0; i < contactsSize; i++) {
            resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private static void readSections(Resume resume, DataInputStream dis) throws IOException {
        int sectionSize = dis.readInt();
        for (int i = 0; i < sectionSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                case ACHIEVEMENT, QUALIFICATIONS -> readListSections(dis, resume, sectionType);
                case EXPERIENCE, EDUCATION -> readCompanySection(dis, resume, sectionType);
            }
        }
    }

    private static void readListSections(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        int listSize = dis.readInt();
        List<String> list = new ArrayList<>(listSize);
        for (int j = 0; j < listSize; j++) {
            list.add(dis.readUTF());
        }
        resume.addSection(sectionType, new ListSection(list));
    }

    private static void readCompanySection(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        int companiesSize = dis.readInt();
        List<Company> companies = new ArrayList<>(companiesSize);
        for (int j = 0; j < companiesSize; j++) {
            String name = dis.readUTF();
            String website = dis.readUTF();
            readPeriods(dis, companies, name, website);
        }
        resume.addSection(sectionType, new CompanySection(companies));
    }

    private static void readPeriods(DataInputStream dis, List<Company> companies, String name, String website) throws IOException {
        int periodsSize = dis.readInt();
        List<Period> periods = new ArrayList<>(periodsSize);
        for (int k = 0; k < periodsSize; k++) {
            String title = dis.readUTF();
            String description = dis.readUTF();
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            periods.add(new Period(title, description, startDate, endDate));
        }
        companies.add(new Company(name, website, periods));
    }
}