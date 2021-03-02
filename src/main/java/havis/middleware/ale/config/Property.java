package havis.middleware.ale.config;

/**
 * Provides property
 */
public class Property {

	/**
	 * Retrieves name
	 */
	String name;

	/**
	 * Retrieves value
	 */
	String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Property(String name, String value) {
		this.name = name;
		this.value = value;
	}
}