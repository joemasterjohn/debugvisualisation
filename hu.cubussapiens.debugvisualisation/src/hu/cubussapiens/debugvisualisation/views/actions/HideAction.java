/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public class HideAction extends GraphAction {

	/**
	 * 
	 */
	public HideAction(GraphViewer viewer) {
		super(viewer);
		setText("Hide");
		setToolTipText("Hide selected nodes");
	}

	@Override
	public void run() {
		IStructuredSelection sel = getSelection();
		IDebugContextInput input = getInput();
		
		for(Object i : sel.toArray()){
			if (i instanceof Integer)
				input.hideNode((Integer)i);
		}
	}

}
