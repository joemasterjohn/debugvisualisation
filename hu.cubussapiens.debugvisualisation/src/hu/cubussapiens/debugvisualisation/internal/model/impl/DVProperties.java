/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model.impl;

import hu.cubussapiens.debugvisualisation.internal.model.IDVProperties;
import hu.cubussapiens.debugvisualisation.internal.model.IPropertyKey;

import java.util.Hashtable;

/**
 *
 */
public abstract class DVProperties implements IDVProperties {

	private Hashtable<IPropertyKey<?>, Object> properties = new Hashtable<IPropertyKey<?>, Object>();

	@SuppressWarnings("unchecked")
	// It is ensured that the values of properties match the type of the key
	public <T> T getProperty(IPropertyKey<T> propertyID) {
		return (T) properties.get(propertyID);

	}

	public boolean isPropertySet(IPropertyKey<?> propertyID) {
		return properties.containsKey(propertyID);
	}

	public <T> void setProperty(IPropertyKey<T> propertyID, T value) {
		properties.put(propertyID, value);
	}

}
