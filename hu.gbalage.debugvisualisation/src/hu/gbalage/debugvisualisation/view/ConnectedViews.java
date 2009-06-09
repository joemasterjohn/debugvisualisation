/**
 * 
 */
package hu.gbalage.debugvisualisation.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.PlatformUI;

/**
 * @author balage
 *
 */
public class ConnectedViews implements ISelectionProvider, ISelectionChangedListener{

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
	
	public ISelectionProvider findSelectionProvider(String vid){
		System.out.println("Trying to find SelectionProvider for "+vid);
		try{
			ISelectionProvider sp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				findView(vid).getSite().getSelectionProvider();
			sp.addSelectionChangedListener(this);
			if (sp == null) System.out.println("No SelectionProvider is given!");
			else System.out.println("SelectionProvider found!");
			return sp;
		}catch(NullPointerException e){
			System.out.println("Can't find View!");
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

	Set<ISelectionChangedListener> listeners = new HashSet<ISelectionChangedListener>();
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		//for(ISelectionProvider sp : getSelectionProviders())
			//sp.addSelectionChangedListener(listener);
		listeners.add(listener);
	}

	public ISelection getSelection() {
		return null;
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		//for(ISelectionProvider sp : getSelectionProviders())
			//sp.removeSelectionChangedListener(listener);
		listeners.remove(listener);
		
	}

	public void setSelection(ISelection selection) {
		for(ISelectionProvider sp : getSelectionProviders())
			sp.setSelection(selection);
 	}

	public void selectionChanged(SelectionChangedEvent event) {
		for(ISelectionChangedListener l : listeners)
			l.selectionChanged(event);
		
	}
	
}
