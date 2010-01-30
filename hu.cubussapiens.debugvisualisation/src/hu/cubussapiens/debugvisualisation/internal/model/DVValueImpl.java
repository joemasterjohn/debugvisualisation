/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Basic implementation of the view model value
 */
public class DVValueImpl extends DVProperties implements IDVValue {

	private final IValue value;

	private final IRootedGraphContentProvider graph;

	private final IDVVariable parent;

	private final IVariable container;

	/**
	 * 
	 * @param value
	 * @param graph
	 */
	protected DVValueImpl(IValue value, IRootedGraphContentProvider graph,
			IDVVariable parent, IVariable container) {
		super();
		this.graph = graph;
		this.value = value;
		this.parent = parent;
		this.container = container;
	}

	/**
	 * Creates a child value. It is not supported to directly initialize values,
	 * use
	 * {@link ViewModelFactory#getValue(IValue, IRootedGraphContentProvider, IDVVariable)}
	 * instead.
	 * 
	 * @param value
	 * @param graph
	 * @param parent
	 */
	public DVValueImpl(IValue value, IRootedGraphContentProvider graph,
			IDVVariable parent) {
		this(value, graph, parent, null);
	}

	/**
	 * Creates a value, which is accessible from the local context. It is not
	 * supported to directly initialize values, use
	 * {@link ViewModelFactory#getValue(IValue, IRootedGraphContentProvider, IVariable)}
	 * instead.
	 * 
	 * @param value
	 * @param graph
	 * @param container
	 */
	public DVValueImpl(IValue value, IRootedGraphContentProvider graph,
			IVariable container) {
		this(value, graph, null, container);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hu.cubussapiens.debugvisualisation.internal.model.IDVValue#getVariables()
	 */
	public IDVVariable[] getVariables() {
		return graph.getEdges(this).toArray(new IDVVariable[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
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

	public boolean isLocalContext() {
		return parent == null;
	}

	public IVariable getContainer() {
		return container == null ? (IVariable) parent
				.getAdapter(IVariable.class) : container;
	}

	public IDVVariable getParent() {
		return parent;
	}

	public IValue getRelatedValue() {
		return value;
	}

	@Override
	protected void finalize() throws Throwable {
		graph.getViewModelFactory().finalize(value);
		super.finalize();
	}
}
