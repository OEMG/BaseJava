package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    private Resume resume;

    public Resume initSections(String uuid, String fullName) {
        resume = new Resume(uuid, fullName);
        initContacts();
        initTextSections();
        initListSections();
        initCompanySections();
        return resume;
    }

    private void initContacts() {
        resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContact(ContactType.HOME_PAGE, "http://gkislin.ru/");
    }

    private void initTextSections() {
        String objective = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям\n";
        resume.addSection(SectionType.OBJECTIVE, new TextSection(objective));
        String personal = "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.\n";
        resume.addSection(SectionType.PERSONAL, new TextSection(personal));
    }

    private void initListSections() {
        initAchievements();
        initQualifications();
    }

    private void initAchievements() {
        List<String> achievements = new ArrayList<>();
        addPoint("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов " +
                "на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект " +
                "для комплексных DIY смет", achievements);
        addPoint("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.", achievements);
        addPoint("Реализация протоколов по приему платежей всех основных платежных системы России" +
                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.", achievements);
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievements));
    }

    private void initQualifications() {
        List<String> qualifications = new ArrayList<>();
        addPoint("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", qualifications);
        addPoint("Version control: Subversion, Git, Mercury, ClearCase, Perforce", qualifications);
        addPoint("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy", qualifications);
        addPoint("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts", qualifications);
        addPoint("Родной русский, английский \"upper intermediate\"", qualifications);
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));
    }

    private void addPoint(String description, List<String> list) {
        list.add(description);
    }

    private void initCompanySections() {
        initExperience();
        initEducation();
    }

    private void initExperience() {
        List<Company> experience = new ArrayList<>();

        List<Period> periods1 = new ArrayList<>();
        experience.add(new Company("Java Online Projects", "https://javaops.ru/", periods1));
        periods1.add(new Period("Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                LocalDate.of(2013, 10, 1), LocalDate.now()));

        List<Period> periods2 = new ArrayList<>();
        experience.add(new Company("Wrike", "https://www.wrike.com/", periods2));
        periods2.add(new Period("Старший разработчик (backend)", "Проектирование и разработка онлайн " +
                "платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1)));

        resume.addSection(SectionType.EXPERIENCE, new CompanySection(experience));
    }

    private void initEducation() {
        List<Company> educations = new ArrayList<>();

        List<Period> periods1 = new ArrayList<>();
        educations.add(new Company("Coursera", "https://www.coursera.org/course/progfun", periods1));
        periods1.add(new Period("'Functional Programming Principles in Scala' by Martin Odersky", "",
                LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1)));

        List<Period> periods2 = new ArrayList<>();
        educations.add(new Company("Luxoft", "https://www.luxoft-training.ru/training/catalog/course.html?ID=22366", periods2));
        periods2.add(new Period("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", "",
                LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1)));

        List<Period> periods3 = new ArrayList<>();
        educations.add(new Company("Санкт-Петербургский национальный исследовательский университет информационных" +
                " технологий, механики и оптики", "https://itmo.ru/", periods3));
        periods3.add(new Period("Аспирантура (программист С, С++)", "",
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1)));
        periods3.add(new Period("Инженер (программист Fortran, C)", "",
                LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1)));

        resume.addSection(SectionType.EDUCATION, new CompanySection(educations));
    }
}