/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.step.input.DigInCommand;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public class DigInAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public DigInAction(GraphViewer viewer) {
		super(viewer);
		setText("Dig in");
	}

	@Override
	public void run() {
		getInput().execute(new DigInCommand(getSelection().toArray()));
	}

}
