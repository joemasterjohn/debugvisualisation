/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import org.eclipse.core.runtime.IAdaptable;

/**
 * View model interface for a variable
 */
public interface IDVVariable extends IAdaptable, IDVProperties {

	/**
	 * Retrieve the value of this variable
	 * 
	 * @return the value
	 */
	public IDVValue getValue();

	/**
	 * Retrieve the parent value of this variable
	 * 
	 * @return the parent value
	 */
	public IDVValue getParent();

}
