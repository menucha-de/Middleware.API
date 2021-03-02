package havis.middleware.ale.reader;

/**
 * Class that represents a set of string describing capabilities of
 * {@link ReaderConnector} implementations.
 */
public class Capability {

	/**
	 * Describe that a {@link ReaderConnector} implementation lost his focus of
	 * a tag after writing into its epc bank.
	 */
	public static final String LostEPCOnWrite = "LostEPCOnWrite";
}