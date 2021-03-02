package havis.middleware.ale.base.message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageHandlerRemote extends Remote {

	void notify(Message message) throws RemoteException;

}
