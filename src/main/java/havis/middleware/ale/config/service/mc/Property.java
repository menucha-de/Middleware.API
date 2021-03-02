package havis.middleware.ale.config.service.mc;

/**
 * Provides the Management properties
 */
public class Property {

	/**
	 * Property for Global
	 */
	public class Global {

		/**
		 * Property for Name
		 */
		public static final String Name = "/Global/Name";

		/**
		 * Property for ALEID
		 */
		public static final String ALEID = "/Global/ALEID";

		/**
		 * Property for SOAPService
		 */
		public static final String SOAPService = "/Global/SOAPService";

		/**
		 * Property for License
		 */
		public static final String License = "/Global/License";

		/**
		 * Property for MaxThreads
		 */
		public static final String MaxThreads = "/Global/MaxThreads";

		/**
		 * Property for ThreadTimeout
		 */
		public static final String ThreadTimeout = "/Global/ThreadTimeout";

		/**
		 * Property for QueueWarningTimeout
		 */
		public static final String QueueWarningTimeout = "/Global/QueueWarningTimeout";

		/**
		 * Property for persist mode
		 */
		public static final String PersistMode = "/Global/PersistMode";

		/**
		 * Property for ReaderCycle
		 */
		public class ReaderCycle {

			/**
			 * Property for Duration
			 */
			public static final String Duration = "/Global/ReaderCycle/Duration";

			/**
			 * Property for Count
			 */
			public static final String Count = "/Global/ReaderCycle/Count";

			/**
			 * Property for Lifetime
			 */
			public static final String Lifetime = "/Global/ReaderCycle/Lifetime";

			/**
			 * Property for extended mode
			 */
			public static final String ExtendedMode = "/Global/ReaderCycle/ExtendedMode";
		}

		public class Subscriber {

			/**
			 * Property for subscriber connect timeout
			 */
			public static final String ConnectTimeout = "/Global/Subscriber/ConnectTimeout";

			/**
			 * Property for subscriber HTTPS security
			 */
			public static final String HttpsSecurity = "/Global/Subscriber/HttpsSecurity";
		}
	}
}
