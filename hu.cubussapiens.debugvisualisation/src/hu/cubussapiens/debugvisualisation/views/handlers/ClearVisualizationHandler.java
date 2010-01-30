/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.handlers;

import hu.cubussapiens.debugvisualisation.internal.api.IRootControl;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 *
 */
public class ClearVisualizationHandler extends AbstractGraphCommandHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IRootControl rootc = (IRootControl) getInput().getAdapter(
				IRootControl.class);
		rootc.clearVisualization();
		return null;
	}

}
