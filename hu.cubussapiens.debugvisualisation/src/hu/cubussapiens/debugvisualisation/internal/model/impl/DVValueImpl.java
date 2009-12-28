/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model.impl;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IValue;

/**
 * Basic implementation of the view model value
 */
public class DVValueImpl implements IDVValue {

	private final IValue value;

	private final IRootedGraphContentProvider graph;

	/**
	 * 
	 * @param value
	 * @param graph
	 */
	public DVValueImpl(IValue value, IRootedGraphContentProvider graph) {
		super();
		this.graph = graph;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.model.IDVValue#getVariables()
	 */
	public IDVVariable[] getVariables() {
		return graph.getEdges(this).toArray(new IDVVariable[0]);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (IValue.class.equals(adapter))
			return value;
		return null;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IValue)
			return value.equals(obj);
		if (obj instanceof IAdaptable) {
			Object a = ((IAdaptable) obj).getAdapter(IValue.class);
			if (a instanceof IValue)
				return value.equals(a);
		}
		return super.equals(obj);
	}

}
