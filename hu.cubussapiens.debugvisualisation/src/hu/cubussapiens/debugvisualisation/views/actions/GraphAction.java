/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public abstract class GraphAction extends Action {

	private final GraphViewer viewer;
	
	/**
	 * Get the viewer
	 * @return
	 */
	protected GraphViewer getViewer(){
		return viewer;
	}
	
	/**
	 * Get the actual input of the action
	 * @return
	 */
	protected IDebugContextInput getInput(){
		Object input = viewer.getInput();
		if (input != null) return (IDebugContextInput)input;
		return null;
	}
	
	/**
	 * Get the current selection of the viewer
	 * @return
	 */
	protected IStructuredSelection getSelection(){
		return (IStructuredSelection)viewer.getSelection();
	}
	
	/**
	 * 
	 */
	public GraphAction(GraphViewer viewer) {
		this.viewer = viewer;
	}

}
