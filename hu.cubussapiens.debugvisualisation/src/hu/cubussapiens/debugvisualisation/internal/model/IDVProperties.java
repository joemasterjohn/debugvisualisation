/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

/**
 * Interface used for storing properties.
 */
public interface IDVProperties {

	/**
	 * Returns whether a property is set.
	 * 
	 * @param propertyID
	 *            the property ID to check
	 * @return true, if a value is stored for the property
	 */
	public boolean isPropertySet(String propertyID);

	/**
	 * Sets a value to a selected property
	 * 
	 * @param propertyID
	 *            the property ID to set
	 * @param value
	 *            the value to set
	 */
	public void setProperty(String propertyID, Object value);

	/**
	 * Returns the value of a selected property
	 * 
	 * @param propertyID
	 *            the property ID to return
	 * @return the value of the selected property
	 */
	public Object getProperty(String propertyID);

}
