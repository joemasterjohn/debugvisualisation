/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel;

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
	public boolean isPropertySet(IPropertyKey<?> propertyID);

	/**
	 * Sets a value to a selected property
	 * 
	 * @param <T>
	 *            Type of property value
	 * 
	 * @param propertyID
	 *            the property ID to set
	 * @param value
	 *            the value to set
	 */
	public <T> void setProperty(IPropertyKey<T> propertyID, T value);

	/**
	 * Returns the value of a selected property
	 * 
	 * @param <T>
	 *            Type of property value
	 * 
	 * @param propertyID
	 *            the property ID to return
	 * @return the value of the selected property
	 */
	public <T> T getProperty(IPropertyKey<T> propertyID);

}
