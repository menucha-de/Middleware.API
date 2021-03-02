package havis.middleware.ale.subscriber;

import havis.middleware.ale.reader.Prefix;

/**
 * Class that represents a set of strings describing properties of a subscriber.
 */
public class Property {

	/**
	 * Class that represents a set of strings describing properties of a
	 * {@link SubscriberConnector} implementation.
	 * 
	 */
	public class Connector {

		/**
		 * Describe the connect timeout
		 */
		public final static String ConnectTimeout = Prefix.Connector + "ConnectTimeout";

		/**
		 * Describe whether to ensure HTTPS security
		 */
		public final static String HttpsSecurity = Prefix.Connector + "HttpsSecurity";
	}
}