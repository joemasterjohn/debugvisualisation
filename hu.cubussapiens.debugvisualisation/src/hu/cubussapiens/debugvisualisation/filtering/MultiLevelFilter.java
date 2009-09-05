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
 * A filter, which can filter nodes in multiple levels. This filter can be used
 * to leave out levels, simplifying the structure of the graph.
 */
public class MultiLevelFilter implements IVariableFilter {

	private final IVariableFilter[] filters;

	/**
	 * Create a multilevel filter by giving a filter for every levels.
	 * 
	 * @param filters
	 *            filters for each level
	 */
	public MultiLevelFilter(IVariableFilter... filters) {
		this.filters = filters;
	}

	private IVariable[] applyLevel(IVariable[] variables, IVariableFilter filter)
			throws DebugException {
		List<IVariable> vs = new ArrayList<IVariable>();

		for (IVariable v : variables) {
			IVariable[] r = filter.filter(v.getValue());
			for (IVariable s : r)
				vs.add(s);
		}

		return vs.toArray(variables);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilter#filter(org.eclipse.debug.core.model.IValue)
	 */
	public IVariable[] filter(IValue value) throws DebugException {
		IVariable[] result = filters[0].filter(value);
		for (int i = 1; i < filters.length; i++)
			result = applyLevel(result, filters[i]);

		return result;
	}

}
