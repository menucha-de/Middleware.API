package havis.middleware.ale.reader;

/**
 * Class that represents a set of strings used as prefixes or namespaces for
 * {@link Property} class.
 *
 */
public class Prefix {

	/**
	 * Retrieves the controller prefix for the ReaderController
	 * properties.
	 */
	public final static String Controller = "Controller.";

	/**
	 * Retrieves the connector prefix for the {@link ReaderConnector}
	 * properties.
	 */
	public final static String Connector = "Connector.";

	/**
	 * Retrieves the reader prefix for physical reader properties.
	 */
	public final static String Reader = "Reader.";
}
