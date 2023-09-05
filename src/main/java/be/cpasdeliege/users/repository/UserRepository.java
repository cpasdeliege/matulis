package be.cpasdeliege.users.repository;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepository {
	
	private final LdapTemplate ldapTemplate;

	public boolean authenticate(String username, String password) {
		return ldapTemplate.authenticate("ou=cpas", "(sAMAccountName="+username+")", password);
	}

	public void updateEmployeeId(Name dn, String employeeId) {
		ModificationItem modificationItem = new ModificationItem(
			DirContextOperations.REPLACE_ATTRIBUTE,
			new BasicAttribute("employeeID", employeeId)
		);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[] { modificationItem });
	}
}
