/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Hides all selected nodes. (calls input.hideNode() on all selected node)
 */
public class HideAction extends GraphSelectionAction {

	/**
	 * 
	 */
	public HideAction(GraphViewer viewer) {
		super(viewer);
		setText("Hide");
		setToolTipText("Hide selected nodes");
	}

	@Override
	protected void run(Integer node, IDebugContextInput input) {
		input.hideNode(node);
	}

}
