package havis.middleware.ale.base.operation;

/**
 * Provides the count for count statistic
 */
public interface ICount extends Statistics {

	/**
	 * Gets the seen count
	 *
	 * @return The seen count
	 */
	int getCount();

	/**
	 * Sets the seen count
	 *
	 * @param count
	 *            The seen count
	 */
	void setCount(int count);
}