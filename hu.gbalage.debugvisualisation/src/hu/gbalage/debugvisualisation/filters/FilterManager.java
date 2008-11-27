/**
 * 
 */
package hu.gbalage.debugvisualisation.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class FilterManager {

	Map<String, IFilter> filters = new HashMap<String, IFilter>();
	
	public FilterManager(){
		//TODO: retrieve extensions
		filters.put("java.lang.Integer", new IntegerFilter());
	}
	
	public static IVariable getVar(IVariable[] vars, String name){
		for(IVariable var : vars){
			try {
				if (var.getName().equals(name)) return var;
			} catch (DebugException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static IVariable[] filterbynames(IVariable[] vars, String[] names){
		List<IVariable> vs = new ArrayList<IVariable>();
		for(String name : names){
			IVariable v = getVar(vars,name);
			if (v != null) vs.add(v);
		}
		IVariable[] variables = new IVariable[vs.size()];
		for(int i=0;i<vs.size();i++) variables[i] = vs.get(i);
		return variables;
		//return (IVariable[])vs.toArray();
	}
	
	public IFilter getFilterForType(String typename){
		if (filters.containsKey(typename))
			return filters.get(typename);
		return null;
	}
	
}
