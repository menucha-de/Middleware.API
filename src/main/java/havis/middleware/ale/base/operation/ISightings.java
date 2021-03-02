package havis.middleware.ale.base.operation;

import havis.middleware.ale.base.operation.tag.Sighting;

import java.util.List;
import java.util.Map;

/**
 * Provides the sightings for sightings statistic
 */
public interface ISightings extends Statistics {

	/**
	 * Gets the sightings
	 *
	 * @return The sightings
	 */
	Map<String, List<Sighting>> getSightings();

	/**
	 * Sets the sightings
	 *
	 * @param sightings
	 *            The sightings
	 */
	void setSightings(Map<String, List<Sighting>> sightings);
}