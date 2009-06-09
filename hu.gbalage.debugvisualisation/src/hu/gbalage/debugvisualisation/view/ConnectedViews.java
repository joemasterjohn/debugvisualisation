/**
 * 
 */
package hu.gbalage.debugvisualisation.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.PlatformUI;

/**
 * @author balage
 *
 */
public class ConnectedViews implements ISelectionProvider{

	public final String[] views = new String[]{
		IDebugUIConstants.ID_VARIABLE_VIEW
	};
	
	private Map<String, ISelectionProvider> sps = new HashMap<String, ISelectionProvider>();
	
	public ISelectionProvider getSelectionProvider(String vid){
		if (!sps.containsKey(vid)) {
			ISelectionProvider sp = findSelectionProvider(vid);
			if (sp == null) return null;
			sps.put(vid, sp);
		}
		return sps.get(vid);
	}
	
	public static ISelectionProvider findSelectionProvider(String vid){
		//System.out.println("Trying to find SelectionProvider for "+vid);
		try{
			ISelectionProvider sp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				findView(vid).getSite().getSelectionProvider();
			//if (sp == null) System.out.println("No SelectionProvider is given!");
			//else System.out.println("SelectionProvider found!");
			return sp;
		}catch(NullPointerException e){
			//System.out.println("Can't find View!");
			return null;
		}
	}

	public Collection<ISelectionProvider> getSelectionProviders(){
		List<ISelectionProvider> ps = new ArrayList<ISelectionProvider>();
		for(String vid : views){
			ISelectionProvider sp = getSelectionProvider(vid);
			if (sp != null) ps.add(sp);
		}
		return ps;
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		for(ISelectionProvider sp : getSelectionProviders())
			sp.addSelectionChangedListener(listener);
	}

	public ISelection getSelection() {
		return null;
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		for(ISelectionProvider sp : getSelectionProviders())
			sp.removeSelectionChangedListener(listener);
		
	}

	public void setSelection(ISelection selection) {
		for(ISelectionProvider sp : getSelectionProviders())
			sp.setSelection(selection);
 	}
	
}
