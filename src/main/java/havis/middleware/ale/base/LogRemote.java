package havis.middleware.ale.base;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Remote interface for logging
 */
public interface LogRemote extends Remote {

	void log(LogRecord record) throws RemoteException;

	Level getLevel() throws RemoteException;

}
