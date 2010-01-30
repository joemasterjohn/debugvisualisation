/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.api.IRootControl;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public class SetRootAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public SetRootAction(GraphViewer viewer) {
		super(viewer);
		setText("Set selection as root of visualization");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		IRootControl dig = (IRootControl) getInput()
				.getAdapter(IRootControl.class);
		dig.digIn(getSelection().toList());
	}

}
