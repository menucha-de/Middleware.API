package havis.middleware.ale.base.operation;

import havis.middleware.ale.base.operation.port.result.Result;
import havis.middleware.ale.base.operation.tag.Tag;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements the event container for port cycle
 */
public class Event extends Data implements ITimestamps, ICount {

	/**
	 * A set of results produced for this port.
	 */
	private Map<Integer, Result> result;

	/**
	 * The time stamp when this tag was seen the first time.
	 */
	private Date firstTime;

	/**
	 * The time stamp when this tag was seen the last time.
	 */
	private Date lastTime;

	/**
	 * The counter how often this tag was seen.
	 */
	private int count;

	/**
	 * Provides the tag for tag events
	 */
	private Tag tag;

	public Map<Integer, Result> getResult() {
		return result;
	}

	public void setResult(Map<Integer, Result> result) {
		this.result = result;
	}

	@Override
	public Date getFirstTime() {
		return firstTime;
	}

	@Override
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	@Override
	public Date getLastTime() {
		return lastTime;
	}

	@Override
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void setCount(int count) {
		this.count = count;
	}

	public Tag getTag() {
		return tag;
	}

	/**
	 * Creates a new instance
	 */
	public Event() {
	    super();
		result = new HashMap<Integer, Result>();
		firstTime = lastTime = new Date();
		count = 0;
	}

	/**
	 * Creates a new instance and assigns the tag uri, and seen timestamps
	 *
	 * @param tag
	 *            The tag
	 */
	public Event(Tag tag) {
		this();
		this.tag = tag;
        setUri(tag.getUri());
        this.firstTime = tag.getFirstTime();
        this.lastTime = tag.getLastTime();
	}

	/**
	 * Creates a new instance and assigns the uri
	 *
	 * @param uri
	 *            The uri
	 */
	public Event(String uri) {
		this();
		setUri(uri);
	}

	/**
	 * Method to set the statistic data for a tag i.e. Readers, Count,
	 *
	 * @param event
	 *            The event object that contains the additional statistic data
	 */
	public void stat(Event event) {
		stat(event, null);
	}

	/**
	 * Method to set the statistic data for a tag i.e. Readers, Count,
	 *
	 * @param event
	 *            The event object that contains the additional statistic data
	 * @param reader
	 *            The reader name
	 */
	public void stat(Event event, String reader) {
		count++;
		if (firstTime == null)
			firstTime = event.firstTime;
		this.lastTime = event.lastTime;
		if (reader != null)
			tag.stat(reader, event.tag);
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * super.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Event))
            return false;
        return true;
    }

	@Override
	public String toString() {
		return "Event [uri=" + getUri() + ", result=" + result + ", firstTime=" + firstTime + ", lastTime=" + lastTime + ", count=" + count + "]";
	}
}
