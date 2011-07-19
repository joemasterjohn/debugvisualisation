/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.handlers.filters;

import hu.cubussapiens.debugvisualisation.views.handlers.AbstractGraphCommandHandler;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Generic filter command
 */
public abstract class ToggleFilterCommand extends AbstractGraphCommandHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			Command command = event.getCommand();
			// TODO HandlerUtil.toggleState() can only be used since 3.5
			State state = command.getState(STATE_ID);
			if (state == null)
				throw new ExecutionException(
						"The command does not have a toggle state"); //$NON-NLS-1$
			if (!(state.getValue() instanceof Boolean))
				throw new ExecutionException(
						"The command's toggle state doesn't contain a boolean value"); //$NON-NLS-1$
	
			boolean oldValue = ((Boolean) state.getValue()).booleanValue();
			state.setValue(new Boolean(!oldValue));
			setState(!oldValue);
			GraphViewer gv = getGraphViewer();
			if (oldValue) {
				gv.removeFilter(getDefinedFilter());
			} else {
				gv.addFilter(getDefinedFilter());
			}
			gv.applyLayout();
		} catch (CoreException e) {
			throw new ExecutionException("Error during command execution", e);
		}
		return null;
	}

	abstract ViewerFilter getDefinedFilter();

	abstract void setState(boolean newState);
}
