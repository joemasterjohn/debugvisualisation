/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * A filter which can filter variables by their names.
 */
public class NameFilter extends AbstractSingleVariableFilter {

	final String name;

	/**
	 * Create a filter which shows only the child variable which has the given
	 * name
	 * 
	 * @param name
	 *            name of the variable to select
	 * @throws IllegalArgumentException
	 *             if the given name is null
	 */
	public NameFilter(String name) {
		if (name == null)
			throw new IllegalArgumentException("variable name cannot be null!");
		this.name = name;
	}

	@Override
	public boolean checkVariable(IValue value, IVariable variable)
			throws DebugException {
		return name.equals(variable.getName());
	}


}
