/**
 * 
 */
package hu.gbalage.debugvisualisation.filters;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class HashMapFilter implements IFilter {

	/**
	 * @see hu.gbalage.debugvisualisation.filters.IFilter#apply(org.eclipse.debug.core.model.IVariable[])
	 */
	public IVariable[] apply(IVariable[] vars) throws CoreException {
		IValue table = FilterManager.getVar(vars, "table").getValue();
		IVariable[] vs = table.getVariables();
		List<IVariable> entries = new ArrayList<IVariable>();
		for (IVariable v : vs){
			IValue ev = v.getValue();
			if (!ev.getValueString().equals("null")) entries.add(v);
		}
		IVariable[] result = new IVariable[entries.size()];
		for(int i=0;i<entries.size();i++) result[i] = entries.get(i);
		return result;
	}

}
