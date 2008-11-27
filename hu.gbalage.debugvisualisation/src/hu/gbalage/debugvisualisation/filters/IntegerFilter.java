/**
 * 
 */
package hu.gbalage.debugvisualisation.filters;

import org.eclipse.debug.core.model.IVariable;

/**
 * Filter for java.lang.Integer. The only relevant field is "value".
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class IntegerFilter implements IFilter {

	/**
	 * @see hu.gbalage.debugvisualisation.filters.IFilter#apply(org.eclipse.debug.core.model.IVariable[])
	 */
	public IVariable[] apply(IVariable[] vars) {
		return FilterManager.filterbynames(vars, new String[]{"value"});
	}

}
