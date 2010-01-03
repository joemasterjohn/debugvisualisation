/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import hu.cubussapiens.debugvisualisation.internal.model.impl.DVValueImpl;
import hu.cubussapiens.debugvisualisation.internal.model.impl.DVVariablesImpl;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.Hashtable;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Factory object for construction view model elements for the debug model.
 */
public class ViewModelFactory {

	private Hashtable<IValue, IDVValue> values = new Hashtable<IValue, IDVValue>();
	private Hashtable<IVariable, IDVVariable> variables = new Hashtable<IVariable, IDVVariable>();

	public IDVValue getValue(IValue value,
			IRootedGraphContentProvider graph, IDVVariable parent) {
		IDVValue newValue;
		if (values.containsKey(value)) {
			newValue = values.get(value);
		} else {
			newValue = new DVValueImpl(value, graph, parent);
			values.put(value, newValue);
		}
		return newValue;
	}

	public IDVValue getValue(IValue value,
			IRootedGraphContentProvider graph, IVariable container) {
		IDVValue newValue;
		if (values.containsKey(value)) {
			newValue = values.get(value);
		} else {
			newValue = new DVValueImpl(value, graph, container);
			values.put(value, newValue);
		}
		return newValue;
	}

	public IDVVariable getVariable(IVariable variable,
			IRootedGraphContentProvider graph, IDVValue parent) {
		IDVVariable newVariable;
		if (variables.containsKey(variable)) {
			newVariable = variables.get(variable);
		} else {
			newVariable = new DVVariablesImpl(variable, graph, parent);
			variables.put(variable, newVariable);
		}
		return newVariable;
	}
}
