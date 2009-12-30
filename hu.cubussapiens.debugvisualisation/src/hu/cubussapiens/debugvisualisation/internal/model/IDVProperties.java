/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

/**
 * Interface used for storing properties.
 */
public interface IDVProperties {

	public boolean isPropertySet(String propertyID);

	public void setProperty(String propertyID, Object value);

	public Object getProperty(String propertyID);

}
