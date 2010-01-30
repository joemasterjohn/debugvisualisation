package hu.cubussapiens.debugvisualisation.views.handlers;

import hu.cubussapiens.debugvisualisation.internal.api.IRootControl;

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

		IRootControl o = (IRootControl) getInput().getAdapter(IRootControl.class);

		o.addVariables(variables);

		return null;
	}

}
