/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IVariable;

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

	/**
	 * Retrieve the debug model element this view model element is based on
	 * 
	 * @return the debug model variable
	 */
	public IVariable getRelatedVariable();
}
