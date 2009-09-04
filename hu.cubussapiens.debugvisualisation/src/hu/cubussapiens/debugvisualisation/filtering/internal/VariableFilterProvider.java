/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering.internal;

import hu.cubussapiens.debugvisualisation.filtering.IVariableFilter;
import hu.cubussapiens.debugvisualisation.filtering.IVariableFilterProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Top level class to retrieve and cache filters
 */
public class VariableFilterProvider implements IVariableFilterProvider {

	private final Map<String, IVariableFilter> cache = new HashMap<String, IVariableFilter>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.IVariableFilterProvider#getFilter(java.lang.String)
	 */
	public IVariableFilter getFilter(String typename) {
		if (cache.containsKey(typename)) {
			return cache.get(typename);
		} else {
			for (IVariableFilterProvider p : VariableFilterProviderExtensionPoint
					.getProviders()) {
				IVariableFilter vf = p.getFilter(typename);
				if (vf != null) {
					cache.put(typename, vf);
					return vf;
				}
			}
			// If we can't find it, won't search it again
			cache.put(typename, null);
		}
		return null;
	}

}
