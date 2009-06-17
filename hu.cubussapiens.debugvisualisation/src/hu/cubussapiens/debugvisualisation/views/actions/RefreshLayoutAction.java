/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.BundleImages;

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
		setImageDescriptor(BundleImages.getInstance().getDescriptor(BundleImages.icon_refresh));
	}

	@Override
	public void run() {
		getViewer().applyLayout();
	}
	
}
