package be.cpasdeliege.users.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupAuthority implements GrantedAuthority{
	private String authority;
}
