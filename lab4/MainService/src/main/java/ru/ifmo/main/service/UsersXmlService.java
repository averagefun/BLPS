package ru.ifmo.main.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ifmo.main.model.User;

@Slf4j
@Service
public class UsersXmlService {
    private final String usersXmlPath;

    public UsersXmlService(@Value("${users.xml.path}") String usersXmlPath) {
        this.usersXmlPath = usersXmlPath;
    }

    public void writeUsersToXml(List<User> userList) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(new Users(userList), new File(usersXmlPath));
        } catch (JAXBException e) {
            log.error("Failed to write users", e);
        }
    }

    public List<User> readUsersFromXml() {
        Users users = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new File(usersXmlPath));
        } catch (JAXBException e) {
            log.error("Failed to read users", e);
        }
        return users != null ? users.getUsers() : new ArrayList<>();
    }

    @Getter
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Users {
        @XmlElement(name = "user")
        private List<User> users;

        public Users() {
            this.users = new ArrayList<>();
        }

        public Users(List<User> users) {
            this.users = users;
        }
    }
}
