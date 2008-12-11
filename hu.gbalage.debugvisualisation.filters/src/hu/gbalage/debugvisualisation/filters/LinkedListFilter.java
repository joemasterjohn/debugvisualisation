/**
 * 
 */
package hu.gbalage.debugvisualisation.filters;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class LinkedListFilter implements IFilter {

	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.filters.IFilter#apply(org.eclipse.debug.core.model.IVariable[])
	 */
	public IVariable[] apply(IVariable[] vars) throws CoreException {
		return FilterManager.filterbynames(vars, new String[]{"header","size"});
	}

}
