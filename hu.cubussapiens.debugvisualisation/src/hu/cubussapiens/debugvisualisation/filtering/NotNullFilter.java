/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Filter which filters out all null values.
 */
public class NotNullFilter extends AbstractSingleVariableFilter {

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.filtering.AbstractSingleVariableFilter#checkVariable(org.eclipse.debug.core.model.IValue, org.eclipse.debug.core.model.IVariable)
	 */
	@Override
	public boolean checkVariable(IValue value, IVariable variable)
			throws DebugException {

		IValue v = variable.getValue();
		return !(("null".equals(v.getReferenceTypeName().trim())) && ("null"
				.equals(v.getValueString())));
	}

}
