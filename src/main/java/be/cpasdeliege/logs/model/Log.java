package be.cpasdeliege.logs.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Log")
@Data
public class Log {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLog;

	@Temporal(TemporalType.DATE)
	@NotNull
    private Date date;

	@Length(max=50)
	@NotNull
	private String username;

	@Length(max=50)
	@NotNull
	private String host;

	@Length(max=50)
	@NotNull
	private String ip;

	@Length(max=50)
	@NotNull
	private String action;

	@Enumerated(EnumType.STRING)
	private LogStatus status;

	@Length(max=50)
	private String message;

	private String oldValue;

	private String newValue;

	@Length(max=50)
	private String table;

	@Length(max=50)
	private String column;

	@Length(max=50)
	private String idTarget;
}
