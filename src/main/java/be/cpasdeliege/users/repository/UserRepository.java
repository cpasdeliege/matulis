package be.cpasdeliege.users.repository;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ConditionCriteria;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Repository;

import be.cpasdeliege.users.model.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepository {
	
	private final LdapTemplate ldapTemplate;

	public boolean authenticate(String username, String password) {
		return ldapTemplate.authenticate("ou=cpas", "(sAMAccountName="+username+")", password);
	}

	public List<User> findUsersByFullname(String fullname) {
		
		ContainerCriteria criteria = LdapQueryBuilder.query()
		.where("dn").is("OU=Administration Centrale,OU=CPAS,dc=cpasliege,dc=dom")
		.or("dn").is("OU=Sites Distants,OU=CPAS,dc=cpasliege,dc=dom");

		LdapQuery query = LdapQueryBuilder.query()
		.base("ou=CPAS")
        //.searchScope(SearchScope.SUBTREE)
        .where("name").like("*"+fullname+"*")
        .and(criteria);

		List<User> users = ldapTemplate.find(query,
        User.class);
		
		System.out.println(users);
		
		return users;
	}

	public void updateEmployeeId(Name dn, String employeeId) {
		ModificationItem modificationItem = new ModificationItem(
			DirContextOperations.REPLACE_ATTRIBUTE,
			new BasicAttribute("employeeID", employeeId)
		);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[] { modificationItem });
	}
}
