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
		filters.put("java.lang.Byte", new NameFilter("value"));
		filters.put("java.lang.Short", new NameFilter("value"));
		filters.put("java.lang.Integer", new NameFilter("value"));
		filters.put("java.lang.Long", new NameFilter("value"));
		filters.put("java.lang.Float", new NameFilter("value"));
		filters.put("java.lang.Double", new NameFilter("value"));
		filters.put("java.lang.Float", new NameFilter("value"));
		
		filters.put("java.util.HashMap<K,V>",new MultiLevelFilter(new IVariableFilter[]{
				new NameFilter("table"),
				new NotNullFilter(),
				new NameFilter("value")
		}, new boolean[]{false,true,false}));
		filters.put("java.util.HashSet<E>", new MultiLevelFilter(new IVariableFilter[]{
				new NameFilter("map"),
				new NameFilter("table"),
				new NotNullFilter(),
				new NameFilter("key")
		}, new boolean[]{false,false,true,false}));
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
