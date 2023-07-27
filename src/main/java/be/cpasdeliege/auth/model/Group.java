package be.cpasdeliege.auth.model;

import java.util.Collection;
import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entry(objectClasses = { "group" }, base = "ou=CPAS")
public class Group {

    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String groupName;

	@Attribute(name = "member")
    private List<String> memberDns;

	public String getDn() {
        return dn.toString();
    }

	@Override
    public String toString() {
        return String.valueOf(dn);
    }

}