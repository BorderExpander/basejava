package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Dev\\basejava\\storageobj");
    protected final Storage STORAGE;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String NAME_1 = "name1";
    private static final String NAME_2 = "name2";
    private static final String NAME_3 = "name3";
    private static final String NAME_4 = "name4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = getCompletedResume(UUID_1, NAME_1);
    private static final Resume RESUME_2 = getCompletedResume(UUID_2, NAME_2);
    private static final Resume RESUME_3 = getCompletedResume(UUID_3, NAME_3);
    private static final Resume RESUME_4 = getCompletedResume(UUID_4, NAME_4);


    public AbstractStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    @Before
    public void setUp() {
        STORAGE.clear();
        STORAGE.save(RESUME_1);
        STORAGE.save(RESUME_2);
        STORAGE.save(RESUME_3);
    }

    @Test
    public void clear() {
        STORAGE.clear();
        assertSize(0);
        List<Resume> allResume = STORAGE.getAllSorted();
        Assert.assertEquals(0, allResume.size());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void save() {
        STORAGE.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        STORAGE.save(new Resume(UUID_1, NAME_1));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        STORAGE.get(UUID_NOT_EXIST);
    }

    @Test
    public void update() {
        String newName = "newName";
        String newSkype = "skype:loginNEW";
        Resume resumeUpdate = new Resume(UUID_1, newName);
        resumeUpdate.addContact(ContactType.SKYPE, newSkype);
        STORAGE.update(resumeUpdate);
        Resume updResume = STORAGE.get(UUID_1);
        Assert.assertEquals(resumeUpdate, updResume);
        Assert.assertEquals(newName, updResume.getFullName());
        Assert.assertEquals(newSkype, updResume.getContact(ContactType.SKYPE));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resumeUpdate = new Resume(UUID_NOT_EXIST, "Name");
        STORAGE.update(resumeUpdate);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        STORAGE.delete(UUID_1);
        assertSize(2);
        STORAGE.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        STORAGE.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(expected, STORAGE.getAllSorted());
        assertSize(3);
    }

    private void assertSize(int size) {
        Assert.assertEquals(STORAGE.size(), size);
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(STORAGE.get(r.getUuid()), r);
    }

    protected static Resume getCompletedResume(String uuid, String name) {
        Resume resume = new Resume(uuid, name);

        //Контакты
        resume.addContact(ContactType.PHONE, "+7(999) 999-9999");
        resume.addContact(ContactType.SKYPE, "skype:login");
        resume.addContact(ContactType.EMAIL, "email@mail.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/linkedin");
        resume.addContact(ContactType.GITHUB, "https://github.com/github");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/stackoverflow");
        resume.addContact(ContactType.HOME_PAGE, "http://homepage.ru/");

        //Позиция
        TextSection objective = new TextSection("Objective");
        resume.addSection(SectionType.OBJECTIVE, objective);

        //Личные каества
        TextSection personal = new TextSection("Personal");
        resume.addSection(SectionType.PERSONAL, personal);

        //Достижения
        ListSection achievement = new ListSection();
        achievement.addValue("achievement1");
        achievement.addValue("achievement2");
        achievement.addValue("achievement3");
        resume.addSection(SectionType.ACHIEVEMENT, achievement);

        //Квалификация
        ListSection qualifications = new ListSection();
        qualifications.addValue("qualifications1");
        qualifications.addValue("qualifications2");
        qualifications.addValue("qualifications3");
        resume.addSection(SectionType.QUALIFICATIONS, qualifications);

        //Опыт работы
        CompanySection companySection = new CompanySection();
        Company company = new Company("company1", "https://company1.ru/");
        Period period = new Period("position1", "position1 desccription1");
        period.setBeginDate(LocalDate.of(2010, 1, 1));
        period.setEndDate(LocalDate.MAX);
        company.addPeriod(period);
        companySection.addCompany(company);

        company = new Company("company2", "https://company2.ru/");
        period = new Period("position2", "Пposition2 desccription2");
        period.setBeginDate(LocalDate.of(2009, 1, 1));
        period.setEndDate(LocalDate.of(2009, 12, 31));
        company.addPeriod(period);
        companySection.addCompany(company);

        resume.addSection(SectionType.EXPERIENCE,companySection);

        //Образование
        CompanySection educationSection = new CompanySection();
        Company education = new Company("education",
                "https://www.education.org/");
        period = new Period("d","education1 desccription1");
        period.setBeginDate(LocalDate.of(2013, 3, 1));
        period.setEndDate(LocalDate.of(2013, 9, 1));
        education.addPeriod(period);
        educationSection.addCompany(education);
        resume.addSection(SectionType.EDUCATION,educationSection);

        return resume;
    }
}