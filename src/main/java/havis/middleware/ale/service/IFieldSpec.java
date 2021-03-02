package havis.middleware.ale.service;

/**
 * Provides the field name datatype and format
 */
public interface IFieldSpec {

	/**
	 * Gets the field name
	 * 
	 * @return The field name
	 */
	String getFieldname();

	/**
	 * Gets the datatype
	 * 
	 * @return Teh datatype
	 */
	String getDatatype();

	/**
	 * Gets the format
	 * 
	 * @return The format
	 */
	String getFormat();
}