package havis.middleware.ale.reader;

import java.util.Observable;

/**
 * Interface to access the process
 */
public interface ReaderConnectorProcess {

	/**
	 * @return the observable which retrieves the remote access
	 */
	Observable get();

	/**
	 * Stop the reader connector process
	 */
	void unget(Observable observable);
}
