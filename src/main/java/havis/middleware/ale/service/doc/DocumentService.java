package havis.middleware.ale.service.doc;

import java.io.InputStream;
import java.io.OutputStream;

public interface DocumentService {

	/**
	 * Check whether a document is available by the specified name
	 * 
	 * @param name
	 *            the name of the document
	 * @return true if the document is available, false otherwise
	 */
	public boolean hasDocument(String name);

	/**
	 * Write the document content to the specified output stream
	 * 
	 * @param name
	 *            the name of the document
	 * @param stream
	 *            the stream to write to
	 * @return true if content was written, false otherwise
	 */
	public boolean writeContent(String name, OutputStream stream);

	/**
	 * Get the document content
	 * 
	 * @param name
	 *            the name of the document
	 * @return the document content or null if the document was not found
	 */
	public InputStream getContent(String name);

	/**
	 * Get the MIME type of the document
	 * 
	 * @param name
	 *            the name of the document
	 * @return the MIME type of the the document or null if the document was not
	 *         found
	 */
	public String getMimetype(String name);

	/**
	 * Get the size of the document
	 * 
	 * @param name
	 *            the name of the document
	 * @return the size of the document or -1 if the document was not found
	 */
	public long getSize(String name);
}