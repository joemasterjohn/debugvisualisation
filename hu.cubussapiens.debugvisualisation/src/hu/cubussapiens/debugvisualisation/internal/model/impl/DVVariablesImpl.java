/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model.impl;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.Hashtable;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IVariable;

/**
 *
 */
public class DVVariablesImpl implements IDVVariable {

	private final IVariable variable;

	private final IRootedGraphContentProvider graph;
	private Hashtable<String, Object> properties = new Hashtable<String, Object>();

	private final IDVValue parent;

	/**
	 * @param variable
	 * @param graph
	 * @param parent
	 */
	public DVVariablesImpl(IVariable variable,
			IRootedGraphContentProvider graph, IDVValue parent) {
		super();
		this.graph = graph;
		this.variable = variable;
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.model.IDVVariable#getValue()
	 */
	public IDVValue getValue() {
		return graph.getEdgeTarget(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (IVariable.class.equals(adapter))
			return variable;
		return null;
	}

	@Override
	public int hashCode() {
		return variable.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IVariable)
			return variable.equals(obj);
		if (obj instanceof IAdaptable) {
			Object a = ((IAdaptable) obj).getAdapter(IVariable.class);
			if (a instanceof IVariable)
				return variable.equals(a);
		}
		return super.equals(obj);
	}

	public IDVValue getParent() {
		return parent;
	}

	public Object getProperty(String propertyID) {
		return properties.get(propertyID);

	}

	public boolean isPropertySet(String propertyID) {
		return properties.containsKey(propertyID);
	}

	public void setProperty(String propertyID, Object value) {
		properties.put(propertyID, value);
	}

	public IVariable getRelatedVariable() {
		return variable;
	}
}
