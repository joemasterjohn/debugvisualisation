/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Action which opens or closes the selected nodes on a graph.
 *
 */
public class ToggleOpenAction extends GraphSelectionAction {
	
	/**
	 * 
	 */
	public ToggleOpenAction(GraphViewer viewer) {
		super(viewer);
		setText("Open/Close");
		setToolTipText("Opens or closes selected nodes");
	}

	@Override
	protected void run(Integer node, IDebugContextInput input) {
		input.toggleOpen(node);
	}

}
