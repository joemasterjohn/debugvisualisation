/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * An abstract filter, which simplifies implementations which wants to filter
 * variables one by one. Checking one variable is done by the checkVariable()
 * method.
 */
public abstract class AbstractSingleVariableFilter implements IVariableFilter {

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilter#filter(org.eclipse.debug.core.model.IValue)
	 */
	public IVariable[] filter(IValue value) throws DebugException {
		List<IVariable> vars = new ArrayList<IVariable>();
		for (IVariable v : value.getVariables())
			if (checkVariable(value, v))
				vars.add(v);
		return vars.toArray(new IVariable[0]);
	}

	/**
	 * Check if the given variable should be displayed as child of the given
	 * value
	 * 
	 * @param value
	 * @param variable
	 * @return true if the variable should be displayed, false otherwise
	 * @throws DebugException
	 */
	public abstract boolean checkVariable(IValue value, IVariable variable)
			throws DebugException;

}
