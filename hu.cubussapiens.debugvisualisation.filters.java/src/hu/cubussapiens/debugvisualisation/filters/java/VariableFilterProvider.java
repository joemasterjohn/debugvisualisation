/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filters.java;

import java.util.HashMap;
import java.util.Map;

import hu.cubussapiens.debugvisualisation.filtering.IVariableFilter;
import hu.cubussapiens.debugvisualisation.filtering.IVariableFilterProvider;
import hu.cubussapiens.debugvisualisation.filtering.MultiLevelFilter;
import hu.cubussapiens.debugvisualisation.filtering.NameFilter;
import hu.cubussapiens.debugvisualisation.filtering.NotNullFilter;

/**
 * @author balage
 *
 */
public class VariableFilterProvider implements IVariableFilterProvider {

	private static final Map<String, IVariableFilter> filters = new HashMap<String, IVariableFilter>();
	
	static{
		filters.put("java.util.ArrayList<E>", new MultiLevelFilter(new IVariableFilter[]{
				new NameFilter("elementData"),
				new NotNullFilter()
		},new boolean[]{false,true}));
		filters.put("java.lang.Integer", new NameFilter("value"));
		filters.put("java.lang.Long", new NameFilter("value"));
		filters.put("java.lang.Double", new NameFilter("value"));
		filters.put("java.lang.Single", new NameFilter("value"));
	}
	
	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilterProvider#getFilter(java.lang.String)
	 */
	public IVariableFilter getFilter(String typename) {
		if (filters.containsKey(typename))
			return filters.get(typename);
		return null;
	}

}
