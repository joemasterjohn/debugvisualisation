package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.views.handlers.AbstractGraphCommandHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;


/**
 *
 */
public class VisualiseVariableHandler extends AbstractGraphCommandHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelection(event);

		List<IVariable> variables = new ArrayList<IVariable>(selection.size());

		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object _var = iterator.next();
			if (_var instanceof IVariable) {
				variables.add((IVariable) _var);
			}
		}

		// TODO:
		// get editor, transformation chain
		// call DigIn transformation step .addVariables

		return null;
	}

}
