package be.cpasdeliege.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import be.cpasdeliege.authentication.model.User;

import javax.naming.directory.SearchControls;
import java.util.List;

@Service
public class UserService {

    private final LdapTemplate ldapTemplate;

    @Autowired
    public UserService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<User> searchUsersByName(String name) {
        String searchFilter = "(cn=" + name + ")";
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return ldapTemplate.search("", searchFilter, searchControls, new UserMapper());
    }
}
