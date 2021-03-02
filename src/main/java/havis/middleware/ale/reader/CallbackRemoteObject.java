package havis.middleware.ale.reader;

import havis.middleware.ale.base.message.Message;
import havis.middleware.ale.base.operation.port.Port;
import havis.middleware.ale.base.operation.tag.Tag;
import havis.util.monitor.ReaderEvent;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Callback remote object to receive notifications from the reader connector
 */
public class CallbackRemoteObject extends UnicastRemoteObject implements CallbackRemote {

	private static final long serialVersionUID = 1L;

	private Callback callback;

	private boolean disposed = false;

	/**
	 * Creates a new callback remote object
	 * 
	 * @param callback
	 *            the callback to feed
	 * @throws RemoteException
	 *             if creation fails
	 */
	public CallbackRemoteObject(Callback callback) throws RemoteException {
		super();
		if (callback == null) {
			throw new NullPointerException("callback must not be null");
		}
		this.callback = callback;
	}

	/**
	 * @return the callback to feed
	 */
	public Callback getCallback() {
		return callback;
	}

	@Override
	public void notify(Message message) throws RemoteException {
		this.callback.notify(message);
	}

	@Override
	public String getName() throws RemoteException {
		return this.callback.getName();
	}

	@Override
	public int getReaderCycleDuration() throws RemoteException {
		return this.callback.getReaderCycleDuration();
	}

	@Override
	public int getNetworkPort() throws RemoteException {
		return this.callback.getNetworkPort();
	}

	@Override
	public void resetNetwortPort(int port) throws RemoteException {
		this.callback.resetNetwortPort(port);
	}

	@Override
	public void notify(long id, Tag tag) throws RemoteException {
		this.callback.notify(id, tag);
	}

	@Override
	public void notify(long id, Port port) throws RemoteException {
		this.callback.notify(id, port);
	}

	@Override
	public void notify(ReaderEvent event) throws RemoteException {
		this.callback.notify(event);
	}

	/**
	 * Disposes the remote object
	 */
	public void dispose() {
		if (!disposed) {
			disposed = true;
			try {
				UnicastRemoteObject.unexportObject(this, false);
			} catch (NoSuchObjectException e) {
				// ignore
			}
		}
	}
}
