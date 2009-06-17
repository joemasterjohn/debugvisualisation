/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public class RefreshLayoutAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public RefreshLayoutAction(GraphViewer viewer) {
		super(viewer);
		setText("Refresh");
		setToolTipText("Refresh layout of graph");
	}

	@Override
	public void run() {
		getViewer().applyLayout();
	}
	
}
