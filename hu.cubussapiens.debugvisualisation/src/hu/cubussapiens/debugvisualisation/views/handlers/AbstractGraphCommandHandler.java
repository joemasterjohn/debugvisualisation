/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.handlers;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.views.DebugVisualisationView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
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

	final static String STATE_ID = "org.eclipse.ui.commands.toggleState"; //$NON-NLS-1$

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

	/**
	 * <p>
	 * Returns the State assigned to the command.
	 * </p>
	 * 
	 * <p>
	 * In case of boolean toggles the value of the state is a {@link Boolean}
	 * variable. An example code is as follows:<br />
	 * 
	 * <code>boolean oldValue = ((Boolean) state.getValue()).booleanValue();</code>
	 * </p>
	 * <p>
	 * TODO HandlerUtil.toggleState() can only be used after Eclipse 3.5
	 * </p>
	 * 
	 * @param command
	 *            the command to check
	 * @return the state the state of the command if defined
	 * @throws ExecutionException
	 *             is thrown if no toggle state is defined for the command
	 */
	protected State getState(Command command) throws ExecutionException {
		State state = command.getState(STATE_ID);
		if (state == null)
			throw new ExecutionException(
					"The command does not have a toggle state"); //$NON-NLS-1$
		return state;
	}

}
