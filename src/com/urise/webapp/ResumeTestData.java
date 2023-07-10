package com.urise.webapp;


import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        //Контакты
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContact(ContactType.HOME_PAGE, "http://gkislin.ru/");

        //Позиция
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, objective);

        //Личные каества
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, personal);

        //Достижения
        ListSection achievement = new ListSection();
        achievement.addValue("Организация команды и успешная реализация Java проектов для сторонних заказчиков:" +
                " приложения автопарк на стеке Spring Cloud/микросервисы," +
                " система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2," +
                " многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");

        achievement.addValue("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                " Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");

        achievement.addValue("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addSection(SectionType.ACHIEVEMENT, achievement);

        //Квалификация
        ListSection qualifications = new ListSection();
        qualifications.addValue("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addValue("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addValue("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle," +
                " MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.addValue("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.addValue("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addSection(SectionType.QUALIFICATIONS, qualifications);

        //Опыт работы
        CompanySection companySection = new CompanySection();
        Company company = new Company("Java Online Projects", "https://javaops.ru/");
        Period period = new Period("Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок");
        period.setBeginDate(LocalDate.of(2013, 10, 1));
        period.setEndDate(LocalDate.MAX);
        company.addPeriod(period);
        companySection.addCompany(company);

        company = new Company("Wrike", "https://www.wrike.com/");
        period = new Period("Старший разработчик (backend)", "Проектирование и разработка онлайн" +
                " платформы управления проектами Wrike" +
                " (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        period.setBeginDate(LocalDate.of(2014, 10, 1));
        period.setEndDate(LocalDate.of(2016, 1, 1));
        company.addPeriod(period);
        companySection.addCompany(company);

        company = new Company("RIT Center", "");
        period = new Period("Java архитектор", "Организация процесса разработки системы ERP" +
                " для разных окружений: релизная политика, версионирование, ведение CI (Jenkins)," +
                " миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO." +
                " Архитектура БД и серверной части системы." +
                " Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices)," +
                " сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                " Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office." +
                " Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2," +
                " xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        period.setBeginDate(LocalDate.of(2012, 4, 1));
        period.setEndDate(LocalDate.of(2014, 10, 1));
        company.addPeriod(period);
        companySection.addCompany(company);
        resume.addSection(SectionType.EXPERIENCE, companySection);

        //Образование
        CompanySection educationSection = new CompanySection();
        Company education = new Company("Coursera",
                "https://www.coursera.org/learn/scala-functional-programming");
        period = new Period("'Functional Programming Principles in Scala' by Martin Odersky");
        period.setBeginDate(LocalDate.of(2013, 3, 1));
        period.setEndDate(LocalDate.of(2013, 3, 1));
        education.addPeriod(period);
        educationSection.addCompany(education);

        education = new Company("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        period = new Period("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
        period.setBeginDate(LocalDate.of(2011, 3, 1));
        period.setEndDate(LocalDate.of(2011, 4, 1));
        education.addPeriod(period);
        educationSection.addCompany(education);

        education = new Company("Siemens AG", "http://www.siemens.ru/");
        period = new Period("3 месяца обучения мобильным IN сетям (Берлин)");
        period.setBeginDate(LocalDate.of(2005, 1, 1));
        period.setEndDate(LocalDate.of(2005, 4, 1));
        education.addPeriod(period);
        educationSection.addCompany(education);
        resume.addSection(SectionType.EDUCATION, educationSection);


        //Вывод
        System.out.println(resume.getFullName());
        for (ContactType type : ContactType.values()) {
            System.out.print(type.getName() + ": ");
            System.out.println(resume.getContact(type));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println("===" + type.getTitle() + "===");
            AbstractSection section = resume.getSection(type);
            switch (type) {
                case PERSONAL, OBJECTIVE -> System.out.println(((TextSection) section).getValue());
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> rows = ((ListSection) section).getValues();
                    for (String row : rows) {
                        System.out.println("*  " + row);
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    List<Company> companies = ((CompanySection) section).getCompanies();
                    for (Company c : companies) {
                        System.out.println(c.getName() + " " + c.getUrl());
                        for (Period p : c.getPeriods()) {
                            System.out.println(p.getBeginDate() + " " + p.getEndDate());
                            System.out.println(p.getName());
                            System.out.println(p.getDescription());
                        }
                        System.out.println();
                    }
                }
            }
        }
    }
}
