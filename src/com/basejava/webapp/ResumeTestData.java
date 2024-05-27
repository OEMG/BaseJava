package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    private static final Resume resume = new Resume("Григорий Кислин");

    public static void main(String[] args) {
        initContacts();
        initTextSections();
        initListSections();
        initCompanySections();
        printContacts();
        printSections();
    }

    private static void initContacts() {
        resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContact(ContactType.HOME_PAGE, "http://gkislin.ru/");
    }

    private static void initTextSections() {
        String objective = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям\n";
        resume.addSection(SectionType.OBJECTIVE, new TextSection(objective));
        String personal = "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.\n";
        resume.addSection(SectionType.PERSONAL, new TextSection(personal));
    }

    private static void initListSections() {
        initAchievements();
        initQualifications();
    }

    private static void initAchievements() {
        List<String> achievements = new ArrayList<>();
        addPoint("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов " +
                "на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект " +
                "для комплексных DIY смет", achievements);
        addPoint("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.", achievements);
        addPoint("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " +
                "Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.", achievements);
        addPoint("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.", achievements);
        addPoint("Реализация c нуля Rich Internet Application приложения на стеке технологий " +
                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического " +
                "трейдинга.", achievements);
        addPoint("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных" +
                " сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации" +
                " о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования" +
                " и мониторинга системы по JMX (Jython/ Django).", achievements);
        addPoint("Реализация протоколов по приему платежей всех основных платежных системы России" +
                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.", achievements);
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievements));
    }

    private static void initQualifications() {
        List<String> qualifications = new ArrayList<>();
        addPoint("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", qualifications);
        addPoint("Version control: Subversion, Git, Mercury, ClearCase, Perforce", qualifications);
        addPoint("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " +
                "SQLite, MS SQL, HSQLDB", qualifications);
        addPoint("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy", qualifications);
        addPoint("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts", qualifications);
        addPoint("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, " +
                "Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, " +
                "Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).", qualifications);
        addPoint("Python: Django.", qualifications);
        addPoint("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js", qualifications);
        addPoint("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka", qualifications);
        addPoint("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, " +
                "DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, " +
                "OAuth2, JWT.", qualifications);
        addPoint("Инструменты: Maven + plugin development, Gradle, настройка Ngnix", qualifications);
        addPoint("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                "Nagios, iReport, OpenCmis, Bonita, pgBouncer", qualifications);
        addPoint("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                "шаблонов, UML, функционального программирования", qualifications);
        addPoint("Родной русский, английский \"upper intermediate\"", qualifications);
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));
    }

    private static void addPoint(String description, List<String> list) {
        list.add(description);
    }

    private static void initCompanySections() {
        initExperience();
        initEducation();
    }

    private static void initExperience() {
        List<Company> experience = new ArrayList<>();

        List<Period> periods1 = new ArrayList<>();
        periods1.add(new Period("Автор проекта.", "Создание, организация и проведение Java онлайн " +
                "проектов и стажировок.", LocalDate.of(2013, 10, 1), LocalDate.now()));
        experience.add(new Company("Java Online Projects", "https://javaops.ru/", periods1));

        List<Period> periods2 = new ArrayList<>();
        periods2.add(new Period("Старший разработчик (backend)", "Проектирование и разработка онлайн " +
                "платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1)));
        experience.add(new Company("Wrike", "https://www.wrike.com/", periods2));

        List<Period> periods3 = new ArrayList<>();
        periods3.add(new Period("Java архитектор", "Организация процесса разработки системы ERP для разных " +
                "окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация " +
                "Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части " +
                "системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего " +
                "назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование " +
                "из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, " +
                "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via " +
                "ssh tunnels, PL/Python",
                LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1)));
        experience.add(new Company("RIT Center", "", periods3));

        resume.addSection(SectionType.EXPERIENCE, new CompanySection(experience));
    }

    private static void initEducation() {
        List<Company> educations = new ArrayList<>();

        List<Period> periods1 = new ArrayList<>();
        periods1.add(new Period("'Functional Programming Principles in Scala' by Martin Odersky", "",
                LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1)));
        educations.add(new Company("Coursera", "https://www.coursera.org/course/progfun", periods1));

        List<Period> periods2 = new ArrayList<>();
        periods2.add(new Period("'Functional Programming Principles in Scala' by Martin OderskyКурс " +
                "'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", "",
                LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1)));
        educations.add(new Company("Luxoft", "https://www.luxoft-training.ru/training/catalog/course.html?ID=22366", periods2));

        List<Period> periods3 = new ArrayList<>();
        periods3.add(new Period("Аспирантура (программист С, С++)", "",
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1)));
        periods3.add(new Period("Инженер (программист Fortran, C)", "",
                LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1)));
        educations.add(new Company("Санкт-Петербургский национальный исследовательский университет информационных" +
                " технологий, механики и оптики", "https://itmo.ru/", periods3));

        resume.addSection(SectionType.EDUCATION, new CompanySection(educations));
    }

    private static void printContacts() {
        System.out.println(resume.getFullName() + "\n");
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + contact.getValue());
        }
    }

    private static void printSections() {
        System.out.println("\n-----------------------------------------------------------------------------------\n\n");
        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            System.out.println(section.getKey().getTitle() + "\n\n" + section.getValue());
        }
    }
}
