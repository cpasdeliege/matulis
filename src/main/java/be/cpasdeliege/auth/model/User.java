package be.cpasdeliege.auth.model;

import java.util.Collection;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import org.springframework.security.core.GrantedAuthority;
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
	//@JsonIgnore
	private Name dn;
	
	@Attribute(name="cn")
	//@DnAttribute(value="cn", index=1)
	private String fullName;

	@Attribute(name = "sn")
	private String username;

	private String password;

	public String getDn() {
        return dn.toString();
    }

	@Override
    public String toString() {
        return String.valueOf(dn);
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
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

	/*
	Voici des requêtes LDAP qui liste les noms des utilisateurs 

Requête pour lister sans filtre
(memberof:=CN=_GPS_UrgenceSociale,OU=Groups,OU=Urgence Sociale,OU=Administration Centrale,OU=CPAS,DC=CpasLiege,DC=dom)

 

Requête pour lister avec filtre
(&(objectClass=user)(memberof:1.2.840.113556.1.4.1941:=CN=_GPS_UrgenceSociale,OU=Groups,OU=Urgence Sociale,OU=Administration Centrale,OU=CPAS,DC=CpasLiege,DC=dom)(name=B*))
(&(objectClass=user)(memberof:1.2.840.113556.1.4.1941:=CN=_GPS_UrgenceSociale,OU=Groups,OU=Urgence Sociale,OU=Administration Centrale,OU=CPAS,DC=CpasLiege,DC=dom)(name=B*)(!(Displayname=*Ben*)))

 

En powershell :

 

Get-ADObject -LDAPFilter "(&(objectClass=user)(memberof:1.2.840.113556.1.4.1941:=CN=_GPS_UrgenceSociale,OU=Groups,OU=Urgence Sociale,OU=Administration Centrale,OU=CPAS,DC=CpasLiege,DC=dom)(name=B*))" -Properties * | Select -ExpandProperty name
BOUTET Olivier
BOURDOUXHE Benoit
BODECCHI Céline

 

rem : -ExpandProperty ne fonctionne qu'avec une seule propriété à la fois

 

Get-ADObject -LDAPFilter "(&(objectClass=user)(memberof:1.2.840.113556.1.4.1941:=CN=_GPS_UrgenceSociale,OU=Groups,OU=Urgence Sociale,OU=Administration Centrale,OU=CPAS,DC=CpasLiege,DC=dom)(name=B*))" -Properties * | Select -Property name,sn,samaccountname

 

name              sn         samaccountname
----              --         --------------
BOUTET Olivier    BOUTET     boutet
BOURDOUXHE Benoit BOURDOUXHE bourdouxhe
BODECCHI Céline   BODECCHI   bodecchi
	*/
}
