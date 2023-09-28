package be.cpasdeliege.users.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	private User user;
	private String errorMessage;
	@Builder.Default
	private HttpStatus status = HttpStatus.OK;
}
