/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.handlers;

import hu.cubussapiens.debugvisualisation.internal.api.ILogicalStructureAdapter;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *
 */
public class AvailableStructureHandler extends AbstractGraphCommandHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String parameter = event
				.getParameter("hu.cubussapiens.debugvisualisation.logicalstructure");
		StructuredSelection selection = (StructuredSelection) HandlerUtil
				.getCurrentSelection(event);
		getInput().clearCache();
		ILogicalStructureAdapter hidden = (ILogicalStructureAdapter) getInput()
				.getAdapter(ILogicalStructureAdapter.class);
		hidden.setLogicalStructure(selection.toList(), parameter);
		return null;
	}

}
