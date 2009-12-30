/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import org.eclipse.core.runtime.IAdaptable;

/**
 * An adaptable view-model interface for a value
 */
public interface IDVValue extends IAdaptable, IDVProperties {

	/**
	 * Get the variables of this value
	 * 
	 * @return the variables of this value
	 */
	public IDVVariable[] getVariables();

}
