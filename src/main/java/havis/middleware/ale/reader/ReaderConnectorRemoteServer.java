package havis.middleware.ale.reader;

import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.base.message.Message;
import havis.middleware.ale.base.operation.port.Port;
import havis.middleware.ale.base.operation.port.PortObservation;
import havis.middleware.ale.base.operation.port.PortOperation;
import havis.middleware.ale.base.operation.tag.Tag;
import havis.middleware.ale.base.operation.tag.TagOperation;
import havis.middleware.ale.service.rc.RCConfig;
import havis.util.monitor.Capabilities;
import havis.util.monitor.CapabilityType;
import havis.util.monitor.Configuration;
import havis.util.monitor.ConfigurationType;
import havis.util.monitor.ReaderEvent;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server for remote connector
 */
public class ReaderConnectorRemoteServer implements ReaderConnectorRemote {

	private ReaderConnector connector;

	private final static Logger log = Logger.getLogger(ReaderConnectorRemoteServer.class.getName());

	/**
	 * Create a new remote connector server
	 * 
	 * @param localConnector
	 *            the local connector instance
	 */
	public ReaderConnectorRemoteServer(ReaderConnector localConnector) {
		this.connector = Objects.requireNonNull(localConnector, "localConnector must not be null");
	}

	@Override
	public void setExtended(boolean extended) throws RemoteException {
		Tag.setExtended(extended);
	}

	@Override
	public void setCallback(final CallbackRemote callback) throws RemoteException {
		this.connector.setCallback(new Callback() {
			@Override
			public void notify(Message message) {
				try {
					callback.notify(message);
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to notify on callback for reader connector server", e);
				}
			}

			@Override
			public void notify(long id, Port port) {
				try {
					callback.notify(id, port);
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to notify on callback for reader connector server", e);
				}
			}

			@Override
			public void notify(long id, Tag tag) {
				try {
					callback.notify(id, tag);
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to notify on callback for reader connector server", e);
				}
			}

			@Override
			public void notify(ReaderEvent event) {
				try {
					callback.notify(event);
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to notify on callback for reader connector server", e);
				}
			}

			@Override
			public String getName() {
				try {
					return callback.getName();
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to get name on callback for reader connector server", e);
				}
				return null;
			}

			@Override
			public int getReaderCycleDuration() {
				try {
					return callback.getReaderCycleDuration();
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to get reader cycle duration on callback for reader connector server", e);
				}
				return -1;
			}

			@Override
			public int getNetworkPort() {
				try {
					return callback.getNetworkPort();
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to get network port on callback for reader connector server", e);
				}
				return -1;
			}

			@Override
			public void resetNetwortPort(int port) {
				try {
					callback.resetNetwortPort(port);
				} catch (RemoteException e) {
					log.log(Level.SEVERE, "Failed to reset network port on callback for reader connector server", e);
				}
			}
		});
	}

	@Override
	public void setProperties(Map<String, String> properties) throws RemoteException, ValidationException, ImplementationException {
		this.connector.setProperties(properties);
	}

	@Override
	public String getCapability(String name) throws RemoteException, ValidationException, ImplementationException {
		return this.connector.getCapability(name);
	}

	@Override
	public void connect() throws RemoteException, ValidationException, ImplementationException {
		this.connector.connect();
	}

	@Override
	public void disconnect() throws RemoteException, ImplementationException {
		this.connector.disconnect();
	}

	@Override
	public void defineTagOperation(long id, TagOperation operation) throws RemoteException, ValidationException, ImplementationException {
		this.connector.defineTagOperation(id, operation);
	}

	@Override
	public void undefineTagOperation(long id) throws RemoteException, ImplementationException {
		this.connector.undefineTagOperation(id);
	}

	@Override
	public void enableTagOperation(long id) throws RemoteException, ImplementationException {
		this.connector.enableTagOperation(id);
	}

	@Override
	public void disableTagOperation(long id) throws RemoteException, ImplementationException {
		this.connector.disableTagOperation(id);
	}

	@Override
	public void executeTagOperation(long id, TagOperation operation) throws RemoteException, ValidationException, ImplementationException {
		this.connector.executeTagOperation(id, operation);
	}

	@Override
	public void abortTagOperation(long id) throws RemoteException, ImplementationException {
		this.connector.abortTagOperation(id);
	}

	@Override
	public void definePortObservation(long id, PortObservation observation) throws RemoteException, ValidationException, ImplementationException {
		this.connector.definePortObservation(id, observation);
	}

	@Override
	public void undefinePortObservation(long id) throws RemoteException, ImplementationException {
		this.connector.undefinePortObservation(id);
	}

	@Override
	public void enablePortObservation(long id) throws RemoteException, ImplementationException {
		this.connector.enablePortObservation(id);
	}

	@Override
	public void disablePortObservation(long id) throws RemoteException, ImplementationException {
		this.connector.disablePortObservation(id);
	}

	@Override
	public void executePortOperation(long id, PortOperation operation) throws RemoteException, ValidationException, ImplementationException {
		this.connector.executePortOperation(id, operation);
	}

	@Override
	public RCConfig getConfig() throws RemoteException, ImplementationException {
		return this.connector.getConfig();
	}

	@Override
	public List<Capabilities> getCapabilities(CapabilityType type) throws RemoteException {
		return this.connector.getCapabilities(type);
	}

	@Override
	public List<Configuration> getConfiguration(ConfigurationType type, short antennaId) throws RemoteException {
		return this.connector.getConfiguration(type, antennaId);
	}

	@Override
	public void setConfiguration(List<Configuration> configuration) throws RemoteException {
		this.connector.setConfiguration(configuration);
	}

	@Override
	public void dispose() throws RemoteException, ImplementationException {
		this.connector.dispose();
	}

}
