package havis.middleware.ale.reader;

import havis.middleware.ale.base.exception.ALEException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.base.operation.port.PortObservation;
import havis.middleware.ale.base.operation.port.PortOperation;
import havis.middleware.ale.base.operation.tag.Tag;
import havis.middleware.ale.base.operation.tag.TagOperation;
import havis.middleware.ale.service.rc.RCConfig;
import havis.util.monitor.Capabilities;
import havis.util.monitor.CapabilityType;
import havis.util.monitor.Configuration;
import havis.util.monitor.ConfigurationType;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client for reader connector remote server
 */
public abstract class ReaderConnectorRemoteClient implements ReaderConnector {

	private static class State {
		public boolean connected;
		public boolean undefined;
		public Callback callback;
		public Map<String, String> properties;
		public Map<Long, TagOperation> tagOperations = new HashMap<Long, TagOperation>();
		public List<Long> enabledTagOperations = new ArrayList<>();
		public Map<Long, PortObservation> portObservations = new HashMap<Long, PortObservation>();
		public List<Long> enabledPortObservations = new ArrayList<>();

		public void mergeProperties(Map<String, String> properties) {
			if (properties != null) {
				if (this.properties == null) {
					this.properties = properties;
				} else {
					this.properties.putAll(properties);
				}
			}
		}
	}

	public static interface Action<T> {
		T invoke() throws RemoteException, ValidationException, ImplementationException;
	}

	private ReaderConnectorProcess process;
	private Observable current;
	protected ReaderConnectorRemote readerConnectorServer;
	private CallbackRemoteObject callbackRemoteObject;

	private String displayName;
	private State currentState = new State();

	protected final static Logger log = Logger.getLogger(ReaderConnectorRemoteClient.class.getName());

	/**
	 * Creates a new reader connector remote client
	 * 
	 * @param process
	 */
	protected ReaderConnectorRemoteClient(ReaderConnectorProcess process) {
		this.process = process;
		startProcess();
	}

	private void startProcess() {
		this.current = this.process.get();
		this.current.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				readerConnectorServer = (ReaderConnectorRemote) arg;
				redefineState();
			}
		});
	}

	private void stopProcess() {
		if (this.current != null) {
			this.process.unget(this.current);
		}
	}

	/**
	 * @return the name of this remote reader connector client
	 */
	protected String getName() {
		if (this.displayName != null)
			return this.displayName;
		return "<unknown>";
	}

	/**
	 * Event which is invoked after the callback and properties have been set
	 * and before the reader is connected again (if it was connected before)
	 * 
	 * @throws ImplementationException
	 *             if redefinition fails
	 */
	protected abstract void onRedefineState() throws ImplementationException;

	private void redefineState() {
		State state = this.currentState;
		this.currentState = new State();
		try {
			this.readerConnectorServer.setExtended(Tag.isExtended());
			if (state.callback != null)
				setCallback(state.callback);
			if (state.properties != null)
				setProperties(state.properties);

			onRedefineState();

			if (state.connected)
				connect();

			for (Entry<Long, TagOperation> entry : state.tagOperations.entrySet()) {
				defineTagOperation(entry.getKey().longValue(), entry.getValue());
			}
			for (Long id : state.enabledTagOperations) {
				enableTagOperation(id.longValue());
			}
			for (Entry<Long, PortObservation> entry : state.portObservations.entrySet()) {
				definePortObservation(entry.getKey().longValue(), entry.getValue());
			}
			for (Long id : state.enabledPortObservations) {
				enablePortObservation(id.longValue());
			}
		} catch (ALEException | RemoteException e) {
			log.log(Level.SEVERE, "Failed to redefine state for reader connector server \"" + getName() + "\": " + e.getMessage());
			this.currentState.undefined = true;
		}
	}

	private <T> T invoke(Action<T> action, boolean handleExceptions) throws ValidationException, ImplementationException {
		if (handleExceptions && this.currentState.undefined)
			throw new ImplementationException("Reader connector server \"" + getName() + "\" is in an undefined state.");
		try {
			return action.invoke();
		} catch (RemoteException e) {
			if (handleExceptions) {
				throw new ImplementationException("Remote connection was lost to server process of reader connector \"" + getName() + "\": " + e.toString());
			}
			return null;
		}
	}

	private <T> T invokeWithoutValidationException(Action<T> action, boolean handleRemoteException) throws ImplementationException {
		try {
			return invoke(action, handleRemoteException);
		} catch (ValidationException e) {
			throw new ImplementationException(e);
		}
	}

	private <T> T invokeWithoutException(Action<T> action, boolean handleRemoteException) {
		try {
			return invoke(action, handleRemoteException);
		} catch (ValidationException | ImplementationException e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected abstract CallbackRemoteObject newCallbackRemoteObject(Callback callback) throws RemoteException;

	@Override
	public void setCallback(Callback callback) {
		try {
			if (this.callbackRemoteObject != null) {
				this.callbackRemoteObject.dispose();
			}
			this.displayName = callback.getName();
			this.currentState.callback = callback;
			this.callbackRemoteObject = newCallbackRemoteObject(callback);
			this.readerConnectorServer.setCallback(this.callbackRemoteObject);
		} catch (RemoteException e) {
			// something is terribly wrong
			this.currentState.undefined = true;
			throw new IllegalStateException("Failed to set callback for reader connector server \"" + getName() + "\": " + e.toString());
		}
	}

	@Override
	public void setProperties(final Map<String, String> properties) throws ValidationException, ImplementationException {
		invoke(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.setProperties(properties);
				return null;
			}
		}, true);
		this.currentState.mergeProperties(properties);
	}

	@Override
	public String getCapability(final String name) throws ValidationException, ImplementationException {
		return invoke(new Action<String>() {
			@Override
			public String invoke() throws RemoteException, ValidationException, ImplementationException {
				return readerConnectorServer.getCapability(name);
			}
		}, true);
	}

	@Override
	public void connect() throws ValidationException, ImplementationException {
		invoke(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.connect();
				return null;
			}
		}, true);
		this.currentState.connected = true;
	}

	@Override
	public void disconnect() throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.disconnect();
				return null;
			}
		}, true);
		this.currentState.connected = false;
	}

	@Override
	public void defineTagOperation(final long id, final TagOperation operation) throws ValidationException, ImplementationException {
		invoke(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.defineTagOperation(id, operation);
				return null;
			}
		}, true);
		this.currentState.tagOperations.put(Long.valueOf(id), operation);
	}

	@Override
	public void undefineTagOperation(final long id) throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.undefineTagOperation(id);
				return null;
			}
		}, true);
		this.currentState.tagOperations.remove(Long.valueOf(id));
	}

	@Override
	public void enableTagOperation(final long id) throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.enableTagOperation(id);
				return null;
			}
		}, true);
		this.currentState.enabledTagOperations.add(Long.valueOf(id));
	}

	@Override
	public void disableTagOperation(final long id) throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.disableTagOperation(id);
				return null;
			}
		}, true);
		this.currentState.enabledTagOperations.remove(Long.valueOf(id));
	}

	@Override
	public void executeTagOperation(final long id, final TagOperation operation) throws ValidationException, ImplementationException {
		invoke(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.executeTagOperation(id, operation);
				return null;
			}
		}, true);
	}

	@Override
	public void abortTagOperation(final long id) throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.abortTagOperation(id);
				return null;
			}
		}, true);
	}

	@Override
	public void definePortObservation(final long id, final PortObservation observation) throws ValidationException, ImplementationException {
		invoke(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.definePortObservation(id, observation);
				return null;
			}
		}, true);
		this.currentState.portObservations.put(Long.valueOf(id), observation);
	}

	@Override
	public void undefinePortObservation(final long id) throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.undefinePortObservation(id);
				return null;
			}
		}, true);
		this.currentState.portObservations.remove(Long.valueOf(id));
	}

	@Override
	public void enablePortObservation(final long id) throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.enablePortObservation(id);
				return null;
			}
		}, true);
		this.currentState.enabledPortObservations.add(Long.valueOf(id));
	}

	@Override
	public void disablePortObservation(final long id) throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.disablePortObservation(id);
				return null;
			}
		}, true);
		this.currentState.enabledPortObservations.remove(Long.valueOf(id));
	}

	@Override
	public void executePortOperation(final long id, final PortOperation operation) throws ValidationException, ImplementationException {
		invoke(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.executePortOperation(id, operation);
				return null;
			}
		}, true);
	}

	@Override
	public RCConfig getConfig() throws ImplementationException {
		return invokeWithoutValidationException(new Action<RCConfig>() {
			@Override
			public RCConfig invoke() throws RemoteException, ValidationException, ImplementationException {
				return readerConnectorServer.getConfig();
			}
		}, true);
	}

	@Override
	public List<Capabilities> getCapabilities(final CapabilityType type) {
		return invokeWithoutException(new Action<List<Capabilities>>() {
			@Override
			public List<Capabilities> invoke() throws RemoteException, ValidationException, ImplementationException {
				return readerConnectorServer.getCapabilities(type);
			}
		}, true);
	}

	@Override
	public List<Configuration> getConfiguration(final ConfigurationType type, final short antennaId) {
		return invokeWithoutException(new Action<List<Configuration>>() {
			@Override
			public List<Configuration> invoke() throws RemoteException, ValidationException, ImplementationException {
				return readerConnectorServer.getConfiguration(type, antennaId);
			}
		}, true);
	}

	@Override
	public void setConfiguration(final List<Configuration> configuration) {
		invokeWithoutException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.setConfiguration(configuration);
				return null;
			}
		}, true);
	}

	@Override
	public synchronized void dispose() throws ImplementationException {
		invokeWithoutValidationException(new Action<Void>() {
			@Override
			public Void invoke() throws RemoteException, ValidationException, ImplementationException {
				readerConnectorServer.dispose();
				return null;
			}

		}, false /* ignore errors, process will be stopped anyway */);
		stopProcess();
	}
}
