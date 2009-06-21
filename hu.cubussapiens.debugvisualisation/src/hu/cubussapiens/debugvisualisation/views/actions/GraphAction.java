package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * An action which can be executed on a GraphViewer, specially the GraphViewer
 * of the DebugVisualisationView. This action assumes that the viewer got an
 * IDebugContextInput as input (or null).
 */
public abstract class GraphAction extends Action {

	private final GraphViewer viewer;

	/**
	 * Get the viewer
	 * 
	 * @return
	 */
	protected GraphViewer getViewer() {
		return viewer;
	}

	/**
	 * Get the actual input of the action
	 * 
	 * @return the actual input
	 */
	protected IDebugContextInput getInput() {
		Object input = viewer.getInput();
		if (input != null)
			return (IDebugContextInput)input;
		return null;
	}

	/**
	 * Get the current selection of the viewer
	 * 
	 * @return the current selection
	 */
	protected IStructuredSelection getSelection() {
		return (IStructuredSelection)viewer.getSelection();
	}

	/**
	 * Creates an action for the given GraphViewer
	 * 
	 * @param viewer
	 */
	public GraphAction(GraphViewer viewer) {
		this.viewer = viewer;
	}

}
