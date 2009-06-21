package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Shows all hidden child nodes of the selected nodes
 */
public class ShowHiddenChildNodesAction extends GraphSelectionAction {

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
	@Override
	protected void run(Integer node, IDebugContextInput input) {
		input.showHiddenChildNodes(node);
	}

}
