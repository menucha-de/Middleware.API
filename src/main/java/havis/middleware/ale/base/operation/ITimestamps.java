package havis.middleware.ale.base.operation;

import java.util.Date;

/**
 * Provides time stamps for time stamp statistic
 */
public interface ITimestamps extends Statistics {

	/**
	 * Gets the firstTime date
	 *
	 * @return The firstTime date
	 */
	public Date getFirstTime();

	/**
	 * Sets the firstTime date
	 *
	 * @param firstTime
	 *            The firstTime date
	 */
	public void setFirstTime(Date firstTime);

	/**
	 * Gets the lastTime date
	 *
	 * @return The lastTime date
	 */
	public Date getLastTime();

	/**
	 * Sets the lastTime date
	 *
	 * @param lastTime
	 *            The firstTime date
	 */
	public void setLastTime(Date lastTime);
}