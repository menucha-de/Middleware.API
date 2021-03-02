package havis.middleware.ale.reader;

import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.base.operation.port.PortObservation;
import havis.middleware.ale.base.operation.port.PortOperation;
import havis.middleware.ale.base.operation.tag.TagOperation;
import havis.middleware.ale.service.rc.RCConfig;
import havis.util.monitor.Capabilities;
import havis.util.monitor.CapabilityType;
import havis.util.monitor.Configuration;
import havis.util.monitor.ConfigurationType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ReaderConnectorRemote extends Remote {

	void setExtended(boolean extended) throws RemoteException;

	void setCallback(CallbackRemote callback) throws RemoteException;

	void setProperties(Map<String, String> properties) throws RemoteException, ValidationException, ImplementationException;

	String getCapability(String name) throws RemoteException, ValidationException, ImplementationException;

	void connect() throws RemoteException, ValidationException, ImplementationException;

	void disconnect() throws RemoteException, ImplementationException;

	void defineTagOperation(long id, TagOperation operation) throws RemoteException, ValidationException, ImplementationException;

	void undefineTagOperation(long id) throws RemoteException, ImplementationException;

	void enableTagOperation(long id) throws RemoteException, ImplementationException;

	void disableTagOperation(long id) throws RemoteException, ImplementationException;

	void executeTagOperation(long id, TagOperation operation) throws RemoteException, ValidationException, ImplementationException;

	void abortTagOperation(long id) throws RemoteException, ImplementationException;

	void definePortObservation(long id, PortObservation observation) throws RemoteException, ValidationException, ImplementationException;

	void undefinePortObservation(long id) throws RemoteException, ImplementationException;

	void enablePortObservation(long id) throws RemoteException, ImplementationException;

	void disablePortObservation(long id) throws RemoteException, ImplementationException;

	void executePortOperation(long id, PortOperation operation) throws RemoteException, ValidationException, ImplementationException;

	RCConfig getConfig() throws RemoteException, ImplementationException;

	List<Capabilities> getCapabilities(CapabilityType type) throws RemoteException;

	List<Configuration> getConfiguration(ConfigurationType type, short antennaId) throws RemoteException;

	void setConfiguration(List<Configuration> configuration) throws RemoteException;

	void dispose() throws RemoteException, ImplementationException;
}