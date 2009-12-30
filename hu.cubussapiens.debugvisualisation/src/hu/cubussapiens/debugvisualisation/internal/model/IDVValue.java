/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

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

	/**
	 * Tells if this value is accessible from the local context
	 * 
	 * @return true if this value is accessible from the local context
	 */
	public boolean isLocalContext();

	/**
	 * Get the parent variable, which contains this value. If the node is
	 * accessible directly from the local context, this will return null
	 * 
	 * @return the parent variable
	 */
	public IDVVariable getParent();

	/**
	 * Get the container variable of the value contained in this node
	 * 
	 * @return the containing variable
	 */
	public IVariable getContainer();

	/**
	 * Returns the debug model element this element is based on
	 * 
	 * @return the debug model value
	 */
	public IValue getRelatedValue();

}
