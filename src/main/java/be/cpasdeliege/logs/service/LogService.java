package be.cpasdeliege.logs.service;

import org.springframework.stereotype.Service;

import be.cpasdeliege.logs.exception.LogException;
import be.cpasdeliege.logs.model.Log;
import be.cpasdeliege.logs.model.LogStatus;
import be.cpasdeliege.logs.repository.LogRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogService {
	private final LogRepository logRepository;


	public void create(String username, String action, LogStatus status){create(username,action,status,null,null,null,null,null,null);}
	public void create(String username, String action, LogStatus status, String message){create(username,action,status,message,null,null,null,null,null);}
	public void create(String username, String action){create(username,action,null,null,null,null,null,null,null);}
	public void create(
		String username, String action,
		LogStatus status, String message, String oldValue, String newValue, String table, String column, String idTarget
	) {
		try {
			Log log = new Log();
			log.setDate(new Date());
			log.setUsername(username);
			log.setAction(action);

            InetAddress localHost = InetAddress.getLocalHost();
			log.setHost(localHost.getHostName());
			log.setIp(localHost.getHostAddress());

			log.setStatus(status);
			log.setOldValue(oldValue);
			log.setNewValue(newValue);
			log.setTable(table);
			log.setColumn(column);
			log.setIdTarget(idTarget);

			logRepository.save(log);

        } catch (UnknownHostException e) {
            e.printStackTrace();
			throw new LogException("Erreur lors de la récupération de l'hôte.");
        }
		
	}
}
