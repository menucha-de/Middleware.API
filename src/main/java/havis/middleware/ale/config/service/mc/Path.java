package havis.middleware.ale.config.service.mc;

/**
 * Provides the Management paths
 */
public class Path {

	/**
	 * Path for global properties
	 */
	public static final String Global = "/Global";

	/**
	 * Path for connectors
	 */
	public class Connector {

		/**
		 * Path for exit connectors
		 */
		public static final String Exit = "/Connector/Exit";

		/**
		 * Path for reader connectors
		 */
		public static final String Reader = "/Connector/Reader";

		/**
		 * Path for subscriber connectors
		 */
		public static final String Subscriber = "/Connector/Subscriber";
	}

	/**
	 * Path for services
	 */
	public class Service {

		/**
		 * Path for LR services
		 */
		public class LR {

			/**
			 * Path for LR version information
			 */
			public static final String Version = "/Service/LR/Version";

			/**
			 * Path for LR logical readers
			 */
			public static final String LogicalReader = "/Service/LR/LogicalReader";
		}

		/**
		 * Path for TM services
		 */
		public class TM {

			/**
			 * Path for TM version information
			 */
			public static final String Version = "/Service/TM/Version";

			/**
			 * Path for TM tag memory fields
			 */
			public static final String TagMemory = "/Service/TM/TagMemory";
		}

		/**
		 * Path for EC services
		 */
		public class EC {

			/**
			 * Path for EC version information
			 */
			public static final String Version = "/Service/EC/Version";

			/**
			 * Path for EC event cycles
			 */
			public static final String EventCycle = "/Service/EC/EventCycle";

			/**
			 * Path for EC subscribers
			 */
			public static final String Subscriber = "/Service/EC/Subscriber";
		}

		/**
		 * Path for CC services
		 */
		public class CC {

			/**
			 * Path for CC version information
			 */
			public static final String Version = "/Service/CC/Version";

			/**
			 * Path for CC command cycles
			 */
			public static final String CommandCycle = "/Service/CC/CommandCycle";

			/**
			 * Path for CC subscribers
			 */
			public static final String Subscriber = "/Service/CC/Subscriber";

			/**
			 * Path for CC epc cache data sources
			 */
			public static final String Cache = "/Service/CC/Cache";

			/**
			 * Path for CC association table data sources
			 */
			public static final String Association = "/Service/CC/Association";

			/**
			 * Path for CC random data sources
			 */
			public static final String Random = "/Service/CC/Random";
		}

		/**
		 * Path for PC services
		 */
		public class PC {

			/**
			 * Path for PC version information
			 */
			public static final String Version = "/Service/PC/Version";

			/**
			 * Path for PC port cycles
			 */
			public static final String PortCycle = "/Service/PC/PortCycle";

			/**
			 * Path for PC subscribers
			 */
			public static final String Subscriber = "/Service/PC/Subscriber";
		}
	}
}
