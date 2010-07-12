/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.DVProperties;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Basic implementation of the view model value
 */
public class DVValueImpl extends DVProperties implements IDVValue {

	private final IValue value;

	private final IRootedGraphContentProvider graph;

	private final Collection<IDVVariable> parents = new HashSet<IDVVariable>();
	private IDVVariable firstParent;
	private boolean localContext = false;

	private final IVariable container;

	/**
	 * Initializes a DV Value implementation. It is not intended to call
	 * directly, use
	 * {@link ViewModelFactory#getValue(IValue, IRootedGraphContentProvider, IVariable, IDVVariable)}
	 * instead.
	 * 
	 * @param value
	 * @param graph
	 * @param parent
	 * @param container
	 */
	public DVValueImpl(IValue value, IRootedGraphContentProvider graph,
			IDVVariable parent, IVariable container) {
		super();
		this.graph = graph;
		this.value = value;
		addParent(parent);
		this.container = container;
		firstParent = parent;
	}

	public void addParent(IDVVariable parent) {
		if (parent != null && !parents.contains(parent))
			parents.add(parent);
	}

	public void removeParent(IDVVariable parent) {
		parents.remove(parent);
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
	 * {@link ViewModelFactory#getValue(IValue, IRootedGraphContentProvider, IVariable, IDVVariable)}
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
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
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

	public void setLocalContext() {
		localContext = true;
	}

	public boolean isLocalContext() {
		return localContext;
	}

	public IVariable getContainer() {
		return container != null ? container : firstParent.getRelatedVariable();
	}

	public IDVVariable getParent() {
		return firstParent;
	}

	public Collection<IDVVariable> getAllParents() {
		return parents;
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
