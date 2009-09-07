/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * A filter which aggregates other filters with the "and" logical operator. This
 * means that this filter selects nodes, which is selected by all of the
 * aggregated filters.
 */
public class AndFilter implements IVariableFilter {

	private final IVariableFilter[] filters;

	/**
	 * Create an "and" filter
	 * 
	 * @param filters
	 */
	public AndFilter(IVariableFilter... filters) {
		this.filters = filters;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilter#filter(org.eclipse.debug.core.model.IValue)
	 */
	public IVariable[] filter(IValue value) throws DebugException {
		List<IVariable> vars = new ArrayList<IVariable>(Arrays
				.asList(filters[0].filter(value)));
		for (int i = 1; i < filters.length; i++) {
			List<IVariable> vs = Arrays.asList(filters[i].filter(value));
			for (IVariable v : vars.toArray(new IVariable[0])) {
				if (!vs.contains(v))
					vars.remove(v);
			}
		}
		return vars.toArray(new IVariable[0]);
	}

}
