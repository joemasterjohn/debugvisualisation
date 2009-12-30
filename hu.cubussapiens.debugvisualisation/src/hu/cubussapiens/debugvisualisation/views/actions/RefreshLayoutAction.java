package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Reapplies the layout of the graph
 */
public class RefreshLayoutAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public RefreshLayoutAction(GraphViewer viewer) {
		super(viewer);
		setText("Refresh");
		setToolTipText("Refresh layout of graph");
		setImageDescriptor(DebugVisualisationPlugin.getDefault().getImageRegistry()
				.getDescriptor(DebugVisualisationPlugin.icon_refresh));
	}

	@Override
	public void run() {
		getViewer().applyLayout();
	}

}
