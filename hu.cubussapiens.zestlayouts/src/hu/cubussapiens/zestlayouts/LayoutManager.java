package hu.cubussapiens.zestlayouts;

import hu.cubussapiens.zestlayouts.LayoutRegistry.LayoutEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.zest.layouts.LayoutAlgorithm;

/**
 * This object manages the registered layout algorithms. When this object is
 * created, it loads all available layout algorithms using a LayoutRegistry.
 * 
 */
public class LayoutManager {

	protected LayoutRegistry reg;

	protected Map<String, LayoutEntry> entries = new HashMap<String, LayoutEntry>();

	/**
	 * Create a LayoutManager and load all available layout algorithms using a
	 * LayoutRegistry.
	 */
	public LayoutManager() {
		reg = new LayoutRegistry();
		for (LayoutEntry entry : reg.getEntries()) {
			entries.put(entry.getID(), entry);
		}
	}

	/**
	 * Extract a LayoutAlgorithm from the given entry
	 * 
	 * @param entry
	 * @return a newly created LayoutAlgorithm
	 */
	public LayoutAlgorithm getLayoutAlgorithm(LayoutEntry entry) {
		return entry.getLayoutCreator().create();
	}

	/**
	 * Create a layout algorithm by its ID
	 * 
	 * @param id
	 * @return a newly created LayoutAlgorithm
	 */
	public LayoutAlgorithm getLayoutAlgorithm(String id) {
		if (entries.containsKey(id)) {
			return getLayoutAlgorithm(entries.get(id));
		} else {
			return null;
		}
	}

	/**
	 * Get a display name of the layout algorithm identified by the given ID
	 * 
	 * @param id
	 * @return name of the layout algorithm
	 */
	public String getLayoutName(String id) {
		if (entries.containsKey(id)) {
			return entries.get(id).getName();
		} else {
			return null;
		}
	}

	/**
	 * @return the IDs of the available layout algorithms
	 */
	public String[] getLayouts() {
		Collection<String> ids = entries.keySet();
		String[] result = new String[ids.size()];
		int i = 0;
		for (String a : ids) {
			result[i] = a;
			i++;
		}
		return result;
	}

	/**
	 * Return the registered layout entries
	 * 
	 * @return All registered entries
	 */
	public LayoutEntry[] getEntries() {
		Collection<String> ids = entries.keySet();
		LayoutEntry[] result = new LayoutEntry[ids.size()];
		int i = 0;
		for (String a : ids) {
			result[i] = entries.get(a);
			i++;
		}
		return result;
	}

	/**
	 * @return the first given layout algorithm
	 */
	public LayoutAlgorithm getDefault() {
		return getLayoutAlgorithm(getDefaultID());
	}

	/**
	 * @return the ID of the first given layout algorithm
	 */
	public String getDefaultID() {
		return getLayouts()[0];
	}

	/**
	 * @return the first registered layout algorithm entry
	 */
	public LayoutEntry getDefaultEntry() {
		return entries.get(getDefaultID());
	}

}
