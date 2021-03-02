package havis.middleware.ale.base.message;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Implements a message used in Callback
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The message type
	 */
	private String type;

	/**
	 * The message text
	 */
	private String text;

	/**
	 * The message exception
	 */
	private Exception exception;

	/**
	 * Creates a new instance
	 */
	public Message() {
	}

	/**
	 * Creates a new instance
	 * 
	 * @param type
	 *            The message type
	 * @param text
	 *            The message text
	 */
	public Message(String type, String text) {
		this.type = type;
		this.text = text;
	}

	/**
	 * Creates a new instance
	 * 
	 * @param type
	 *            The message type
	 * @param text
	 *            The message text
	 * @param exception
	 *            The message exception
	 */
	public Message(String type, String text, Exception exception) {
		this.type = type;
		this.text = text;
		this.setException(exception);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param type
	 *            The message type
	 * @param exception
	 *            The message exception
	 */
	public Message(String type, Exception exception) {
		this.type = type;
		this.text = String.format("[%s]:\n%s\n%s", exception.getClass(), exception.getMessage(), Arrays.asList(exception.getStackTrace()));
		this.setException(exception);

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
}
