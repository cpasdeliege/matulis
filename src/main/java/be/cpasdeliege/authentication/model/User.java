package be.cpasdeliege.authentication.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entry(objectClasses = { "person" }, base = "ou=CPAS")
public class User implements UserDetails {
	
	@Id
	private Name dn;
	
	@Attribute(name="cn")
	private String fullName;
	
	@Attribute(name = "sn")
	private String username;
	
	private boolean authenticated; // prop test à supp

	@Attribute(name = "memberOf")
	private Collection<GroupAuthority> authorities;

	private String password;


	public String getDn() {
        return dn.toString();
    }

	public void setAuthorities(List<String> authoritiesStrings) {
		authorities = new ArrayList<>();
		authoritiesStrings.forEach(authority -> authorities.add(new GroupAuthority(authority)));
	}

	public boolean hasAuthority(String authority) {
		return authorities.stream().anyMatch(groupAuthority -> groupAuthority.getAuthority().equals(authority));
	}

	public boolean hasAuthorities(List<String> authoritiesStrings) {
		return authorities.stream().anyMatch(groupAuthority -> authoritiesStrings.contains(groupAuthority.getAuthority()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
    public String toString() {
        return String.valueOf(dn);
    }

}
