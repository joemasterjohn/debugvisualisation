package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Hides all selected nodes. (calls input.hideNode() on all selected node)
 */
public class HideAction extends GraphSelectionAction {

	/**
	 * Creates an action, which can hide all selected nodes on a graph
	 * 
	 * @param viewer
	 */
	public HideAction(GraphViewer viewer) {
		super(viewer);
		setText("Hide");
		setToolTipText("Hide selected nodes");
	}

	@Override
	protected void run(Integer node, StackFrameContextInput input) {
		// input.hideNode(node);
		// TODO: implement this on stack frame context input
	}

}
