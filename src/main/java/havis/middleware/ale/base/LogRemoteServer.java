package havis.middleware.ale.base;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Server for logging remotely
 */
public class LogRemoteServer implements LogRemote {

	public final static String NAME = "LogServer";

	private final static Logger log = Logger.getLogger(LogRemoteServer.class.getName());

	private Registry registry;

	/**
	 * Creates a new log remote server
	 * 
	 * @param registry
	 *            the registry to bind to
	 * @throws RemoteException
	 *             if binding fails
	 */
	public LogRemoteServer(Registry registry) throws RemoteException {
		super();
		if (registry == null) {
			throw new NullPointerException("registry must not be null");
		}
		this.registry = registry;
		this.registry.rebind(NAME, UnicastRemoteObject.exportObject(this, 0));
	}

	@Override
	public void log(LogRecord record) throws RemoteException {
		log.log(record);
	}

	@Override
	public Level getLevel() throws RemoteException {
		for (Level level : new Level[] { Level.FINEST, Level.FINER, Level.FINE, Level.CONFIG, Level.INFO, Level.WARNING, Level.SEVERE }) {
			if (log.isLoggable(level)) {
				return level;
			}
		}
		return Level.OFF;
	}

	public void dispose() {
		try {
			this.registry.unbind(NAME);
		} catch (RemoteException | NotBoundException e) {
			// ignore
		}
	}
}
