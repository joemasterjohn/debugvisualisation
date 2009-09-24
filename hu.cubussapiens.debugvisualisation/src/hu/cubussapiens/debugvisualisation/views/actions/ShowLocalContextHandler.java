/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;

/**
 * Handler toggling the display of the show local context handler filter
 */
public class ShowLocalContextHandler extends AbstractHandler {
	
	public final static String STATE_ID = "org.eclipse.ui.commands.toggleState"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
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
		return null;
	}

}
