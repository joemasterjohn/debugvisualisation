/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IVariable;

/**
 *
 */
public class DVVariablesImpl extends DVProperties implements IDVVariable {

	private final IVariable variable;

	private final IRootedGraphContentProvider graph;

	private final IDVValue parent;

	/**
	 * Creates a view model variable node. It is not supported to create
	 * variables using this constructor - use
	 * {@link ViewModelFactory#getVariable(IVariable, IRootedGraphContentProvider, IDVValue)}
	 * instead.
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hu.cubussapiens.debugvisualisation.internal.model.IDVVariable#getValue()
	 */
	public IDVValue getValue() {
		return graph.getEdgeTarget(this);
	}

	/*
	 * (non-Javadoc)
	 * 
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

	public IVariable getRelatedVariable() {
		return variable;
	}

	@Override
	protected void finalize() throws Throwable {
		graph.getViewModelFactory().finalize(variable);
		super.finalize();
	}
}
