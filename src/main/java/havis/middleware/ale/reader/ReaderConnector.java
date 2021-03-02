package havis.middleware.ale.reader;

import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.base.operation.port.PortObservation;
import havis.middleware.ale.base.operation.port.PortOperation;
import havis.middleware.ale.base.operation.tag.TagOperation;
import havis.middleware.ale.service.rc.RCConfig;
import havis.util.monitor.ReaderSource;

import java.util.Map;

/**
 * Interface that defines all methods need by all reader connector
 * implementations. The interface also defines the {@link Callback} interface
 * as callback contract, which is used by a reader connector implementations to
 * deliver tag, port and/or error information asynchronous to the
 * ReaderController.
 *
 * A connector instance will be initialized as soon as a client defines a
 * logical base reader with the reader type property, the connector
 * implementation provides. For each reader a single instance of the connector
 * will be generated. During the instance creation the specific reader connector
 * will get a concrete {@link Callback} implementation as callback channel.
 *
 * To register a reader, a connector entry with reader type, assembly and class
 * name has to be created and enabled within the middleware configuration.
 */
public interface ReaderConnector extends ReaderSource {

	/**
	 * Sets the controller callback
	 *
	 * @param callback
	 *            The controller callback
	 */
	void setCallback(Callback callback);

	/**
	 * Operation to initializes the reader based on information from parameter
	 * properties.
	 *
	 * The parameter properties is used by a reader connector implementation to
	 * configure a instance on initialization.
	 *
	 * @param properties
	 *            A set of reader properties.
	 * @throws ValidationException
	 *             if a validation error occurred.
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void setProperties(Map<String, String> properties)
			throws ValidationException, ImplementationException;

	/**
	 * Operation to get a specific capability from the reader connector by name.
	 *
	 * @param name
	 *            The name of the requested capability.
	 * @throws ValidationException
	 *             if a validation error occurred.
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	String getCapability(String name) throws ValidationException,
			ImplementationException;

	/**
	 * Operation to establish the connection to the physical reader.
	 *
	 * @throws ValidationException
     *             if the configuration is invalid.
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void connect() throws ValidationException, ImplementationException;

	/**
	 * Operation to disconnect from the physical reader.
	 *
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void disconnect() throws ImplementationException;

	/**
	 * Operation to define a new tag operation on the reader connector, using a
	 * unique id defined by the ReaderController to identify a specific
	 * tag operation.
	 *
	 * The parameter id is used by a reader controller as a correlation id, the
	 * same id is used by a {@link ReaderConnector} implementation to deliver a
	 * tag through the {@link Callback} interface.
	 *
	 * This operation is used to read requested tag information during the
	 * inventory of the physical reader.
	 *
	 * @param id
	 *            The unique id to identify the tag operation.
	 * @param operation
	 *            The tag operation to define.
	 * @throws ValidationException
	 *             " if a validation error occurred.
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void defineTagOperation(long id, TagOperation operation)
			throws ValidationException, ImplementationException;

	/**
	 * Operation to undefine a tag operation on the reader connector, using the
	 * unique id of the specific tag operation.
	 *
	 * @param id
	 *            The unique id to identify the tag operation
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void undefineTagOperation(long id) throws ImplementationException;

	/**
	 * Operation to enable a tag operation on the reader connector. After this
	 * method the reader connector will report result using the
	 * {@link Callback} interface.
	 *
	 * @param id
	 *            The unique id to identify the tag operation
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void enableTagOperation(long id) throws ImplementationException;

	/**
	 * Operation to disable a tag operation on the reader connector. After this
	 * method the reader connector will no longer report results.
	 *
	 * @param id
	 *            The unique id to identify the reader operation
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void disableTagOperation(long id) throws ImplementationException;

	/**
	 * Operation to define a tag operation that is executed directly without the
	 * need of enabling the operation.
	 *
	 * This operation is used to execute any kind of operation i.e. read, write,
	 * lock after the reader connector acquired a tag during the inventory and
	 * reported the tag information.
	 *
	 * @param id
	 *            The unique id to identify the tag operation.
	 * @param operation
	 *            The tag operation to be execute.
	 * @throws ValidationException
	 *             if a validation error occurred.
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void executeTagOperation(long id, TagOperation operation)
			throws ValidationException, ImplementationException;

	/**
	 * Operation to abort a running tag operation previously defined by the
	 * ExecuteTagOperation operation.
	 *
	 * @param id
	 *            The unique id to identify the tag operation.
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void abortTagOperation(long id) throws ImplementationException;

	/**
	 * Operation to define a new port observation on the reader connector, using
	 * a unique id defined by the ReaderController to identify a
	 * specific port observation.
	 *
	 * The parameter id is used by a reader controller as a correlation id, the
	 * same id is used by a {@link ReaderConnector} implementation to deliver a
	 * port thought the {@link Callback} interface.
	 *
	 * This operation is used to observe specific ports of a physical reader.
	 *
	 * @param id
	 *            The unique id to identify the port observation.
	 * @param observation
	 *            The port observation to define.
	 * @throws ValidationException
	 *             Should be thrown by the implementation if a validation error
	 *             occurred.
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void definePortObservation(long id, PortObservation observation)
			throws ValidationException, ImplementationException;

	/**
	 * Operation to undefine a port observation on the reader connector, using
	 * the unique id of the specific port observation.
	 *
	 * @throws id
	 *             The unique id to identify the port observation
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void undefinePortObservation(long id) throws ImplementationException;

	/**
	 * Operation to enable a port observation on the reader connector. After
	 * this method the reader connector will report result using the
	 * {@link Callback} interface.
	 *
	 * @param id
	 *            The unique id to identify the port observation
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void enablePortObservation(long id) throws ImplementationException;

	/**
	 * Operation to disable a port observation on the reader connector. After
	 * this method the reader connector will no longer report results.
	 *
	 * @param id
	 *            The unique id to identify the port observation
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void disablePortObservation(long id) throws ImplementationException;

	/**
	 * Operation to define a port operation that is executed directly without
	 * the need of enabling the operation.
	 *
	 * This method is used to get/or set the state of a specific port on a
	 * physical reader once.
	 *
	 * @param id
	 *            The unique id to identify the tag operation.
	 * @param operation
	 *            The tag operation to be execute.
	 * @throws ValidationException
	 *             if a validation error occurred.
	 * @throws ImplementationException
	 *             if any unexpected error occurred.
	 */
	void executePortOperation(long id, PortOperation operation)
			throws ValidationException, ImplementationException;

	/**
	 * Operation to get the complete reader configuration as a tree structure
	 * defined by the {@link RCConfig} class.
	 *
	 * @return The requested reader configuration
	 * @throws ImplementationException
	 *            Should be thrown by the implementation if any unexpected error
	 *            occurred.
	 */
	RCConfig getConfig() throws ImplementationException;

	/**
	 * Operation to dispose the reader connector instance and release all its
	 * resources.
	 *
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void dispose() throws ImplementationException;
}
