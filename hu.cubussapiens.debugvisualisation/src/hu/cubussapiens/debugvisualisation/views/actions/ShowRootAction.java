/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.api.IDigInNodes;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public class ShowRootAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public ShowRootAction(GraphViewer viewer) {
		super(viewer);
		if (getInput() != null)
			setEnabled(true);
		setText("Show local context node");
		setImageDescriptor(DebugVisualisationPlugin.getDefault().getImageRegistry()
				.getDescriptor(DebugVisualisationPlugin.icon_root));
	}

	@Override
	public void run() {
		IDigInNodes dig = (IDigInNodes) getInput()
				.getAdapter(IDigInNodes.class);
		dig.showRoot();
	}

}
