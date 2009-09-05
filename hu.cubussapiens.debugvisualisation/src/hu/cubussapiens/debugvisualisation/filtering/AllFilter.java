/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 *
 */
public class AllFilter implements IVariableFilter {

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilter#filter(org.eclipse.debug.core.model.IValue)
	 */
	public IVariable[] filter(IValue value) throws DebugException {
		return value.getVariables();
	}

}
