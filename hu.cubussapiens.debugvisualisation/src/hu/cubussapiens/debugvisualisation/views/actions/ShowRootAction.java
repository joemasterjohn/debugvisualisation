/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.internal.step.input.ShowRootCommand;

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
		setText("Show local context node");
		setImageDescriptor(Activator.getDefault().getImageRegistry()
				.getDescriptor(Activator.icon_root));
	}

	@Override
	public void run() {
		getInput().execute(new ShowRootCommand());
	}

}
