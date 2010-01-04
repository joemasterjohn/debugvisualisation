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

	/**
	 * Returns the view model representation of an IValue. If no such
	 * representation exists, a new one is created. This call is used for
	 * creating an element with an already existing parent in the view model.
	 * 
	 * @param value
	 * @param graph
	 * @param parent
	 * @return the view model representation of the value
	 */
	public IDVValue getValue(IValue value, IRootedGraphContentProvider graph,
			IDVVariable parent) {
		IDVValue newValue;
		if (values.containsKey(value)) {
			newValue = values.get(value);
		} else {
			newValue = new DVValueImpl(value, graph, parent);
			values.put(value, newValue);
		}
		return newValue;
	}

	/**
	 * Returns the view model representation of an IValue. If no such
	 * representation exists, a new one is created. This is used for creating a
	 * top-level element that is accessible from the local context.
	 * 
	 * @param value
	 * @param graph
	 * @param container
	 * @return the view model representation of the value
	 */
	public IDVValue getValue(IValue value, IRootedGraphContentProvider graph,
			IVariable container) {
		IDVValue newValue;
		if (values.containsKey(value)) {
			newValue = values.get(value);
		} else {
			newValue = new DVValueImpl(value, graph, container);
			values.put(value, newValue);
		}
		return newValue;
	}

	/**
	 * Returns the view model representation of the value, if exists, but does
	 * not initialize a new one.
	 * 
	 * @param value
	 * @return the view model representation of the value, if exists, or null
	 *         otherwise
	 */
	public IDVValue getValue(IValue value) {
		return values.get(value);
	}

	/**
	 * Returns the view model representation of the variable, if exists, and
	 * initializes a new one if necessary.
	 * 
	 * @param variable
	 * @param graph
	 * @param parent
	 * @return the view model representation of the value
	 */
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

	/**
	 * Returns the view model representation of the variable, if exists, but
	 * does not create a new one otherwise.
	 * 
	 * @param variable
	 * @return the view model representation of the variable, if exists, or null
	 *         otherwise.
	 */
	public IDVVariable getVariable(IVariable variable) {
		return variables.get(variable);
	}
}
