package havis.middleware.ale.subscriber;

import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.InvalidURIException;
import havis.middleware.ale.service.cc.CCReports;
import havis.middleware.ale.service.ec.ECReports;
import havis.middleware.ale.service.pc.PCReports;

import java.net.URI;
import java.util.Map;

/**
 * This interface is used by all subscriber connectors. A connector instance
 * will be initialized as soon as a client subscribes with the scheme, the
 * connector provides. For each subscriber a single instance of the connector
 * will generated. To register a subscriber, a connector entry with scheme name,
 * assembly and class name has to be created and enabled. If a report is ready
 * for delivery the {@code CallbackResults} method will be called.
 *
 */
public interface SubscriberConnector {

	/**
	 * Initializes the subscriber based on information from parameter
	 * 
	 * @param uri
	 *            The subscriber URI. The parameter is used by a subscriber
	 *            implementation to configure a instance on initialization. For
	 *            example the implementation can request a query specific
	 *            attribute within the URI, like a path to an configuration
	 *            file.
	 * @param properties
	 *            The properties
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 * @throws InvalidURIException
	 *             Should be thrown by the implementation if URI did not match
	 *             the requirements of the subscriber implementation.
	 */
	void init(URI uri, Map<String, String> properties)
			throws InvalidURIException, ImplementationException;

	/**
	 * Delivers the reports.
	 *
	 * @param reports
	 *            The report to deliver
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
	void send(ECReports reports) throws ImplementationException;

	/**
	 * Delivers the reports.
	 *
	 * @param reports
	 *            The report to deliver
	 * @throws ImplementationException
	 *             Should be thrown by the implementation if any unexpected
	 *             error occurred.
	 */
    void send(CCReports reports) throws ImplementationException;

    /**
     * Delivers the reports.
     *
     * @param reports
     *            The report to deliver
     * @throws ImplementationException
     *             Should be thrown by the implementation if any unexpected
     *             error occurred.
     */
    void send(PCReports reports) throws ImplementationException;

    /**
     * Disposes the instance. A subscriber implementation should use this method
     * to release all resources it owns. It should also release all resources
     * owned by its base types by calling parents Dispose.
     *
     * @throws ImplementationException
     *             Should be thrown by the implementation if any unexpected
     *             error occurred.
     */
    void dispose() throws ImplementationException;
}