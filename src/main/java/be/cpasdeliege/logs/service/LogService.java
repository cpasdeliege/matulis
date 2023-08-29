package be.cpasdeliege.logs.service;

import org.springframework.stereotype.Service;

import be.cpasdeliege.logs.model.Log;
import be.cpasdeliege.logs.model.LogStatus;
import be.cpasdeliege.logs.repository.LogRepository;

import java.util.Date;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogService {
	private final LogRepository logRepository;

	public void create(String username, String action, String ip){create(username,action,ip,null,null,null,null,null,null,null);}
	public void create(String username, String action, String ip, LogStatus status){create(username,action,ip,status,null,null,null,null,null,null);}
	public void create(String username, String action, String ip, LogStatus status, String statusMessage){create(username,action,ip,status,statusMessage,null,null,null,null,null);}
	public void create(
		String username, String action, String ip,
		LogStatus status, String statusMessage, String entityType, String entityId, String propertyName, String originalValue, String newValue
	) {
		Log log = new Log();
		log.setDateTime(new Date());
		log.setUsername(username);
		log.setAction(action);
		log.setIp(ip);

		log.setStatus(status);
		log.setStatusMessage(statusMessage);
		log.setEntityType(entityType);
		log.setEntityId(entityId);
		log.setPropertyName(propertyName);
		log.setOriginalValue(originalValue);
		log.setNewValue(newValue);

		logRepository.save(log);
	}
}
