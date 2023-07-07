package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamResumeSerializer implements ResumeSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(dos, r.getSections().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getValue());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) entry.getValue()).getValues();
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companyList = ((CompanySection) entry.getValue()).getCompanies();
                        writeWithException(dos, companyList, company -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getUrl());
                            List<Period> periods = company.getPeriods();
                            writeWithException(dos, periods, period -> {
                                dos.writeUTF(period.getName());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getBeginDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            });
                        });
                    }
                }

            });
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, WriteElement<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.writeElement(element);
        }
    }

    @FunctionalInterface
    private interface WriteElement<T> {
        void writeElement(T element) throws IOException;
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private void readItems(DataInputStream dis, ReadItem reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.readElement();
        }
    }

    @FunctionalInterface
    private interface ReadItem {
        void readElement() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ReadElement<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readElement());
        }
        return list;
    }

    @FunctionalInterface
    private interface ReadElement<T> {
        T readElement() throws IOException;
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE, PERSONAL -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListSection(readList(dis, dis::readUTF));
            }
            case EXPERIENCE, EDUCATION -> {
                return new CompanySection(
                        readList(dis, () -> new Company(dis.readUTF(), dis.readUTF(),
                                readList(dis, () -> new Period(dis.readUTF(), dis.readUTF(), LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()))))));
            }
        }
        return null;
    }
}
