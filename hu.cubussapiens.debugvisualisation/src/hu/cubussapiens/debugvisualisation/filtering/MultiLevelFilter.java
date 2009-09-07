/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

import hu.cubussapiens.debugvisualisation.filtering.internal.MultiLevelVariable;

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

	private final boolean[] nameMask;

	/**
	 * Create a multilevel filter by giving a filter for every levels.
	 * 
	 * @param filters
	 *            filters for each level
	 * @param nameMask
	 *            tells if a node selected by the filter will give a name part
	 *            to the resulting variable path
	 */
	public MultiLevelFilter(IVariableFilter[] filters, boolean[] nameMask) {
		this.filters = filters;
		this.nameMask = nameMask;
	}

	private List<List<IVariable>> applyLevel(List<List<IVariable>> vars,
			IVariableFilter filter)
			throws DebugException {

		List<List<IVariable>> result = new ArrayList<List<IVariable>>();

		for (List<IVariable> path : vars) {
			IVariable v = path.get(path.size() - 1);
			IVariable[] r = filter.filter(v.getValue());
			for (IVariable s : r) {
				List<IVariable> l = new ArrayList<IVariable>(path);
				l.add(s);
				result.add(l);
			}
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilter#filter(org.eclipse.debug.core.model.IValue)
	 */
	public IVariable[] filter(IValue value) throws DebugException {
		IVariable[] result = filters[0].filter(value);

		List<List<IVariable>> vars = new ArrayList<List<IVariable>>();
		for (IVariable v : result) {
			List<IVariable> l = new ArrayList<IVariable>();
			l.add(v);
			vars.add(l);
		}

		for (int i = 1; i < filters.length; i++)
			vars = applyLevel(vars, filters[i]);

		IVariable[] vs = new IVariable[vars.size()];

		for (int i = 0; i < vs.length; i++)
			vs[i] = new MultiLevelVariable(vars.get(i)
					.toArray(new IVariable[0]), nameMask);

		return vs;
	}

}
