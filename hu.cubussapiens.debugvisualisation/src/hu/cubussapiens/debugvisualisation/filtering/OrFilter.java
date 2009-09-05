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
 * This filter aggregates an array of filter with the "or" logical operator,
 * which causes that all variables fill be selected, which is selected by at
 * least one of the given filters.
 */
public class OrFilter implements IVariableFilter {

	private final IVariableFilter[] filters;

	/**
	 * Constructs an "or" filter aggregating the given filters.
	 * 
	 * @param filters
	 */
	public OrFilter(IVariableFilter... filters) {
		this.filters = filters;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilter#filter(org.eclipse.debug.core.model.IValue)
	 */
	public IVariable[] filter(IValue value) throws DebugException {
		List<IVariable> vs = new ArrayList<IVariable>();

		for (IVariableFilter f : filters) {
			IVariable[] vss = f.filter(value);
			for (IVariable v : vss) {
				if (!vs.contains(v))
					vs.add(v);
			}
		}
		return vs.toArray(new IVariable[0]);
	}

}
