/**
 * 
 */
package hu.gbalage.debugvisualisation.filters;

import org.eclipse.debug.core.model.IVariable;

/**
 * A filter is applicable on the sub-variables of a Value. When the filter
 * is applied, it sorts out unnecessary and unimportant variables and 
 * defines a new structure for them. This is used to form the data into a
 * easily displayable structure.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface IFilter {

	/**
	 * Apply the filter on the given array of variables.
	 * @param vars
	 * @return
	 */
	public IVariable[] apply(IVariable[] vars);
	
}
