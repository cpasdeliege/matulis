package be.cpasdeliege.users.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entry(objectClasses = { "person" }, base = "ou=CPAS")
public final class User implements UserDetails {
	
	@Id
	private Name dn;
	
	@Attribute(name="cn")
	private String fullname;
	
	@Attribute(name = "sAMAccountName")
	private String username;
	
	@Attribute(name = "memberOf")
	@JsonIgnore
	private Collection<GroupAuthority> authorities;

	@Attribute(name = "employeeId")
	private String employeeId;

	@JsonIgnore
	private String password;
	@JsonIgnore
	boolean accountNonExpired;
	@JsonIgnore
	boolean accountNonLocked;
	@JsonIgnore
	boolean credentialsNonExpired;
	@JsonIgnore
	boolean enabled;

	public String getDn() {
        return dn.toString();
    }

	public void setAuthorities(List<String> authoritiesStrings) {
		authorities = new ArrayList<>();
		authoritiesStrings.forEach(authority -> authorities.add(new GroupAuthority(authority)));
	}

	public boolean hasAnyAuthority(List<GroupAuthority> allowedAuthorities) {
		boolean hasAnyAuthority = false;
		for (GroupAuthority authority : authorities) {
			for (GroupAuthority allowedAuthority : allowedAuthorities) {
				if(allowedAuthority.getAuthority().equalsIgnoreCase(authority.getAuthority())){
					hasAnyAuthority = true;
				}
			}
		};
		return hasAnyAuthority;
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
