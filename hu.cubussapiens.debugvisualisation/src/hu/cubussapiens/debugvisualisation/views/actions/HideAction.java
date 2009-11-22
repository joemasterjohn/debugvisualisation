package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.api.IHiddenNodes;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Hides all selected nodes. (calls input.hideNode() on all selected node)
 */
public class HideAction extends GraphAction {

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

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		IHiddenNodes hidden = (IHiddenNodes) getInput().getAdapter(
				IHiddenNodes.class);
		hidden.hideNodes(getSelection().toList());
	}

}
