package be.cpasdeliege.users.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Repository;

import be.cpasdeliege.users.model.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepository {
	
	private final LdapTemplate ldapTemplate;

	public boolean authenticate(String username, String password) {
		boolean authenticated = false;
		authenticated = authenticate(User.BASE1, username, password);
		if(!authenticated) {
			authenticated = authenticate(User.BASE2, username, password);
		}
		return authenticated;
	}

	public User findOneByDn(Name dn) {
		User user = ldapTemplate.findByDn(dn,User.class);
		return user;
	}

	public User findOneByUsername(String username) {		
		LdapQuery query1 = getUsernameQuery(User.BASE1, username);
		LdapQuery query2 = getUsernameQuery(User.BASE2, username);

		User user = null;
		try {
			user = ldapTemplate.findOne(query1,User.class);
		} catch (EmptyResultDataAccessException e) {
			try {
				user = ldapTemplate.findOne(query2,User.class);
			} catch (EmptyResultDataAccessException e2) {
				// aucun utilisateur trouvé
			}
		}
		
		return user;
	}

	public List<User> findAllByFullname(String fullname) {		
		LdapQuery query1 = getFullnameQuery(User.BASE1, fullname);
		LdapQuery query2 = getFullnameQuery(User.BASE2, fullname);

		List<User> users1 = ldapTemplate.find(query1,User.class);
		List<User> users2 = ldapTemplate.find(query2,User.class);
		
		List<User> users = new ArrayList<>();
		users.addAll(users1);
		users.addAll(users2);

		Collections.sort(users, Comparator.comparing(User::getFullname));
		
		return users;
	}

	public void updateEmployeeId(Name dn, String employeeId) {
		ModificationItem modificationItem = new ModificationItem(
			DirContextOperations.REPLACE_ATTRIBUTE,
			new BasicAttribute("employeeID", employeeId)
		);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[] { modificationItem });
	}

	/* PRIVATE FUNCTIONS */

	private LdapQuery getUsernameQuery(String base, String username) {
		return LdapQueryBuilder.query().base(base).where("sAMAccountName").is(username);
	}

	private LdapQuery getFullnameQuery(String base, String fullname) {
		return LdapQueryBuilder.query().base(base).where("name").like("*"+fullname+"*");
	}

	private boolean authenticate(String base, String username, String password) {
		return ldapTemplate.authenticate(base, "(sAMAccountName="+username+")", password);
	}

}
