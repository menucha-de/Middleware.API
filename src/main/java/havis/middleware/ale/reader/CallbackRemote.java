package havis.middleware.ale.reader;

import havis.middleware.ale.base.message.MessageHandlerRemote;
import havis.middleware.ale.base.operation.port.Port;
import havis.middleware.ale.base.operation.tag.Tag;
import havis.util.monitor.ReaderEvent;

import java.rmi.RemoteException;

public interface CallbackRemote extends MessageHandlerRemote {

	String getName() throws RemoteException;

	int getReaderCycleDuration() throws RemoteException;

	int getNetworkPort() throws RemoteException;

	void resetNetwortPort(int port) throws RemoteException;

	void notify(long id, Tag tag) throws RemoteException;

	void notify(long id, Port port) throws RemoteException;

	void notify(ReaderEvent event) throws RemoteException;

}