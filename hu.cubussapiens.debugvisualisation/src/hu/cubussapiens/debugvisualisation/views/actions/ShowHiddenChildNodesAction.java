package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.api.IHiddenNodes;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Shows all hidden child nodes of the selected nodes
 */
public class ShowHiddenChildNodesAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public ShowHiddenChildNodesAction(GraphViewer viewer) {
		super(viewer);
		setText("Show hidden child nodes");
		setToolTipText("Show all hidden child nodes of selected nodes");
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		IHiddenNodes hidden = (IHiddenNodes) getInput().getAdapter(
				IHiddenNodes.class);
		hidden.showHiddenChildNodes(getSelection().toList());
	}

}
