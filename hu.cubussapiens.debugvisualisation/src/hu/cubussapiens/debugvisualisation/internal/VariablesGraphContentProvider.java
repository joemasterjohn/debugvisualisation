/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import java.util.ArrayList;
import java.util.Set;

import hu.cubussapiens.debugvisualisation.internal.input.IDCInputChangeListener;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider;

/**
 * @author balage
 *
 */
public class VariablesGraphContentProvider implements IGraphEntityRelationshipContentProvider, IDCInputChangeListener {

	IDebugContextInput input = null;
	
	GraphViewer viewer = null;

	public void dispose() {
		if (input != null) input.removeDCInputChangeListener(this);
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	
		if (input != null) input.removeDCInputChangeListener(this);
		
		if (viewer instanceof GraphViewer)
			this.viewer = (GraphViewer)viewer;
		
		if (newInput instanceof IDebugContextInput){
			this.input = (IDebugContextInput)newInput;
			input.addDCInputChangeListener(this);
		}
		
	}

	public Object[] getRelationships(Object source, Object dest) {
		Set<IVariable> vs = input.getRelations((Integer)source, (Integer)dest);
		ArrayList<IVariable> l = new ArrayList<IVariable>(vs);
		return l.toArray();
	}

	public Object[] getElements(Object inputElement) {
		Set<Integer> ns = input.getVisibleNodes();
		ArrayList<Integer> l = new ArrayList<Integer>(ns);
		return l.toArray();
	}

	public void openStateChanged(Integer node, boolean open) {
		viewer.refresh();
		viewer.applyLayout();
	}

	public void refreshed() {
		viewer.refresh();
		viewer.applyLayout();
	}

}
