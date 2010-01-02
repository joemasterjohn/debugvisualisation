/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.handlers;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.views.DebugVisualisationView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * An abstract handler class capable of returning the graph viewer and the debug
 * visualisation view.
 */
public abstract class AbstractGraphCommandHandler extends AbstractHandler {

	/**
	 * Returns the debug visualisation view. If it is not open, opens it.
	 * 
	 * @return the reference to the debug visualisation view
	 */
	protected DebugVisualisationView getView() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView(DebugVisualisationPlugin.VIEW_ID);

		if (view == null) {
			try {
				page.showView(DebugVisualisationPlugin.VIEW_ID);
			} catch (PartInitException e) {
			}
		}

		return (DebugVisualisationView) view;

	}

	/**
	 * Returns the graph viewer component from the debug visualisation view.
	 * 
	 * @return the graph viewer component
	 */
	protected GraphViewer getGraphViewer() {
		DebugVisualisationView view = getView();
		return view.getGraphViewer();
	}

	/**
	 * Get the actual input of the action
	 * 
	 * @return the actual input
	 */
	protected StackFrameContextInput getInput() {
		Object input = getGraphViewer().getInput();
		if (input != null)
			return (StackFrameContextInput) input;
		return null;
	}

}
