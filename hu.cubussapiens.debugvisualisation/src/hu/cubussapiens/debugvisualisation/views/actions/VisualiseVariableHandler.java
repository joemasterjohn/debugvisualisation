package hu.cubussapiens.debugvisualisation.views.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 
 */

/**
 *
 */
public class VisualiseVariableHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelection(event);
		// TODO here should be service called to add the selected elements to
		// the visualisation
		// selection.toArray()
		/*
		 * Iterator<?> iterator = selection.iterator(); while
		 * (iterator.hasNext()) { Object _var = iterator.next(); if (_var
		 * instanceof IVariable) {
		 * 
		 * } }
		 */
		return null;
	}

}
