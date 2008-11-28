/**
 * 
 */
package hu.gbalage.debugvisualisation.layouts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import hu.gbalage.debugvisualisation.layouts.LayoutRegistry.LayoutEntry;

import org.eclipse.zest.layouts.LayoutAlgorithm;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class LayoutManager {

	protected LayoutRegistry reg;
	
	protected Map<String, LayoutEntry> entries = new HashMap<String, LayoutEntry>();
	
	public LayoutManager(){
		reg = new LayoutRegistry();
		for(LayoutEntry entry : reg.getEntries()){
			entries.put(entry.getID(), entry);
		}
	}
	
	public LayoutAlgorithm getLayoutAlgorithm(LayoutEntry entry){
		return entry.getLayoutCreator().create();
	}
	
	public LayoutAlgorithm getLayoutAlgorithm(String id){
		if (entries.containsKey(id)){
			return getLayoutAlgorithm(entries.get(id));
		}else{
			return null;
		}
	}
	
	public String getLayoutName(String id){
		if (entries.containsKey(id)){
			return entries.get(id).getName();
		}else{
			return null;
		}
	}
	
	/**
	 * @return the ids of the available layout algorithms
	 */
	public Collection<String> getLayouts(){
		return entries.keySet();
	}
	
	/**
	 * @return the first given layout algorithm
	 */
	public LayoutAlgorithm getDefault(){
		return getLayoutAlgorithm(reg.getEntries().iterator().next());
	}
	
}
