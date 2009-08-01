package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.step.input.ToggleOpenNodeCommand;

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
		getInput().execute(new ToggleOpenNodeCommand(getSelection().toArray()));
	}

}
