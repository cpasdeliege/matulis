package be.cpasdeliege.authentication.service;

import org.springframework.ldap.core.AttributesMapper;

import be.cpasdeliege.authentication.model.User;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class UserMapper implements AttributesMapper<User> {

    @Override
    public User mapFromAttributes(Attributes attributes) throws NamingException {
        User user = new User();
        user.setUsername(attributes.get("cn").get().toString());
        user.setFirstName(attributes.get("givenName").get().toString());
        user.setLastName(attributes.get("sn").get().toString());
        return user;
    }
}
