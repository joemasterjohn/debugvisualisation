/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Action which opens or closes the selected nodes on a graph
 *
 */
public class ToggleOpenAction extends Action {

	GraphViewer viewer;
	
	/**
	 * 
	 */
	public ToggleOpenAction(GraphViewer viewer) {
		this.viewer = viewer;
		setText("Open/Close");
		setToolTipText("Opens or closes selected nodes");
	}

	@Override
	public void run() {
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		IDebugContextInput input = (IDebugContextInput)viewer.getInput();
		
		for(Object i : sel.toArray()){
			if (i instanceof Integer)
				input.toggleOpen((Integer)i);
		}
	}

}
