/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.api.IRootControl;

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
		setText("Show local context");
		setImageDescriptor(DebugVisualisationPlugin.getDefault().getImageRegistry()
				.getDescriptor(DebugVisualisationPlugin.icon_root));
	}

	@Override
	public void run() {
		IRootControl dig = (IRootControl) getInput()
				.getAdapter(IRootControl.class);
		dig.showRoot();
	}

}
