package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Defines an action which should be executed on all of the selected nodes in
 * the graph one by one.
 */
public abstract class GraphSelectionAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public GraphSelectionAction(GraphViewer viewer) {
		super(viewer);
	}

	@Override
	public final void run() {
		IStructuredSelection sel = getSelection();
		StackFrameContextInput input = getInput();

		for (Object i : sel.toArray()) {
			run(i, input);
		}
	}

	protected abstract void run(Object node, StackFrameContextInput input);

}
