package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.api.IOpenCloseNodes;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Action which opens or closes the selected nodes on a graph.
 * 
 */
public class ToggleOpenAction extends GraphAction {

	/**
	 * Creates a ToggleOpenAction
	 * 
	 * @param viewer
	 */
	public ToggleOpenAction(GraphViewer viewer) {
		super(viewer);
		setText("Open/Close");
		setToolTipText("Opens or closes selected nodes");
	}

	@Override
	public void run() {
		IOpenCloseNodes ocn = (IOpenCloseNodes) getInput().getAdapter(
				IOpenCloseNodes.class);
		if (!getSelection().isEmpty())
			ocn.toggleOpenNode(getSelection().getFirstElement());
	}

}
