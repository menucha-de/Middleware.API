package havis.middleware.ale.reader;

/**
 * Class that represents a set of strings describing properties of a
 * LogicalReader, the ReaderController class and
 * {@link ReaderConnector} implementations.
 */
public class Property {

	/**
	 * Describe the reader type, which determine the {@link ReaderConnector}
	 * implementation for a logical base reader.
	 */
	public final static String ReaderType = "ReaderType";

	/**
	 * Describe the antennaID property, which is used to make antenna
	 * restrictions on logical composite reader.
	 */
	public final static String AntennaID = "AntennaID";

	/**
	 * Describe the glimpsed timeout property, which is used for tag smoothing
	 * within logical readers.
	 */
	public final static String GlimpsedTimeout = "GlimpsedTimeout";

	/**
	 * Describe the observed time out property, which is used for tag smoothing
	 * within logical readers.
	 */
	public final static String ObservedTimeThreshold = "ObservedTimeThreshold";

	/**
	 * Describe the observed count property, which is used for tag smoothing
	 * within logical readers.
	 */
	public final static String ObservedCountThreshold = "ObservedCountThreshold";

	/**
	 * Describe the lost timeout property, which is used for tag smoothing within
	 * logical readers.
	 */
	public final static String LostTimeout = "LostTimeout";

	/**
	 * Class that represents a set of strings describing properties of a
	 * {@link ReaderConnector} implementation.
	 *
	 */
	public class Connector {
		/**
		 * Describe the connection type, which determine the connection type
		 * that is used by the {@link ReaderConnector} implementation for
		 * communication with a physical reader ie TCP.
		 */
		public final static String ConnectionType = Prefix.Connector
				+ "ConnectionType";

		/**
		 * Describe the host name, which is used by the {@link ReaderConnector}
		 * implementation for communication with a physical reader over TCP
		 * protocol.
		 */
		public final static String Host = Prefix.Connector + "Host";

		/**
		 * Describe the port id, which is used by the {@link ReaderConnector}
		 * implementation for communication with a physical reader over TCP and
		 * COMM protocol.
		 */
		public final static String Port = Prefix.Connector + "Port";

		/**
		 * Describe the device id, which is used by the {@link ReaderConnector}
		 * implementation for communication with a physical reader over USB
		 * protocol.
		 */
		public final static String DeviceID = Prefix.Connector + "DeviceID";

		/**
		 * Describe the timeout value between a request and response from a
		 * {@link ReaderConnector} implementation to a physical reader.
		 */
		public final static String Timeout = Prefix.Connector + "Timeout";
	}

	/**
	 * Class that represents a set of strings describing properties of the
	 * ReaderController class.
	 */
	public static class Controller {

		/**
		 * Describe the timeout value between a request and response from the
		 * ReaderController to a {@link ReaderConnector} implementation.
		 */
		public final static String Timeout = Prefix.Controller + "Timeout";

		/**
		 * Whether to optimize the write operations sent to the
		 * {@link ReaderConnector}
		 */
		public final static String OptimizeWriteOperations = Prefix.Controller + "OptimizeWriteOperations";

		/**
		 * The delay in milliseconds to wait before any reconnect attempt. If
		 * the connection to the reader is lost, the ReaderController will
		 * attempt an indefinite amount of reconnect attempts.
		 */
		public final static String ReconnectDelay = Prefix.Controller + "ReconnectDelay";
	}
}
