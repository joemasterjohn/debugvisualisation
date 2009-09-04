/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * A filter which can be applied to a value of a specific type, and tells that
 * which variables should be displayed as childs of the value.
 */
public interface IVariableFilter {

	/**
	 * Apply this filter on the given value. This method will return all
	 * variables which should be displayed according to this filter.
	 * 
	 * @param value
	 * @return filtered variables
	 * @throws DebugException
	 */
	public IVariable[] filter(IValue value) throws DebugException;

}
