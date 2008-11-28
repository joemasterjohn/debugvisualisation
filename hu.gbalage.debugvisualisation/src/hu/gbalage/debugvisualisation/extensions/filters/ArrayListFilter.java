/**
 * 
 */
package hu.gbalage.debugvisualisation.extensions.filters;

import hu.gbalage.debugvisualisation.filters.FilterManager;
import hu.gbalage.debugvisualisation.filters.IFilter;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ArrayListFilter implements IFilter {

	/**
	 * @see hu.gbalage.debugvisualisation.filters.IFilter#apply(org.eclipse.debug.core.model.IVariable[])
	 */
	public IVariable[] apply(IVariable[] vars) {
		IVariable sizeVar = FilterManager.getVar(vars, "size");
		IVariable dataVar = FilterManager.getVar(vars, "elementData");
		
		int size = 0;
		try {
			size = Integer.parseInt(sizeVar.getValue().getValueString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		
		IVariable[] data = new IVariable[0];
		try {
			data = dataVar.getValue().getVariables();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		
		IVariable[] result = new IVariable[size];
		
		for(int i=0;i<size;i++) if(i<data.length) result[i] = data[i];
		
		return result;
	}

}
