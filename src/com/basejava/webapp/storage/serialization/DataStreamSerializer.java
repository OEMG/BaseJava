package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Map<ContactType, String> contacts = resume.getContacts();
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private static void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        Map<SectionType, AbstractSection> sections = resume.getSections();
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            SectionType sectionType = entry.getKey();
            AbstractSection section = entry.getValue();
            dos.writeUTF(sectionType.name());
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getDescription());
                case ACHIEVEMENT, QUALIFICATIONS -> writeListSection((ListSection) section, dos);
                case EXPERIENCE, EDUCATION -> writeCompanySection((CompanySection) section, dos);
            }
        }
    }

    private static void writeListSection(ListSection section, DataOutputStream dos) throws IOException {
        List<String> list = section.getList();
        dos.writeInt(list.size());
        for (String point : list) {
            dos.writeUTF(point);
        }
    }

    private static void writeCompanySection(CompanySection section, DataOutputStream dos) throws IOException {
        List<Company> companies = section.getCompanies();
        dos.writeInt(companies.size());
        for (Company company : companies) {
            dos.writeUTF(company.getName());
            dos.writeUTF(company.getWebsite());
            writePeriods(company, dos);
        }
    }

    private static void writePeriods(Company company, DataOutputStream dos) throws IOException {
        List<Period> periods = company.getPeriods();
        dos.writeInt(periods.size());
        for (Period period : periods) {
            dos.writeUTF(period.getTitle());
            dos.writeUTF(period.getDescription());
            dos.writeUTF(String.valueOf(period.getStartDate()));
            dos.writeUTF(String.valueOf(period.getEndDate()));
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