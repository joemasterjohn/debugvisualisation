package hu.cubussapiens.debugvisualisation.internal.step;

import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;

/**
 * An abstract, partial implementation of a transformation step. This class
 * implements the common features like handling listeners and delegating
 * commands.
 */
public abstract class AbstractGraphTransformationStep implements
		IGraphTransformationStep, IGraphChangeListener, IAdaptable {

	private final IRootedGraphContentProvider parent;
	protected final ViewModelFactory factory;

	/**
	 * 
	 * @param parent
	 * @param factory
	 */
	public AbstractGraphTransformationStep(IRootedGraphContentProvider parent,
			ViewModelFactory factory) {
		this.parent = parent;
		this.factory = factory;
		if (parent instanceof IGraphTransformationStep) {
			((IGraphTransformationStep) parent).addListener(this);
		}
	}

	public void graphChanged(IGraphChangeEvent event) {
		// simply delegate event
		trigger(event);
	}

	private final Set<IGraphChangeListener> listeners = new HashSet<IGraphChangeListener>();

	public void addListener(IGraphChangeListener listener) {
		listeners.add(listener);
	}

	public IRootedGraphContentProvider getParent() {
		return parent;
	}

	public void removeListener(IGraphChangeListener listener) {
		listeners.remove(listener);
	}

	protected final void trigger(IGraphChangeEvent event) {
		for (IGraphChangeListener l : listeners)
			l.graphChanged(event);
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		Object o = tryAdapter(adapter);
		if (o != null)
			return o;
		if (parent != null)
			return parent.getAdapter(adapter);
		return null;
	}

	/**
	 * Override this method to provide adapter interfaces
	 * 
	 * @param adapter
	 * @return
	 */
	protected Object tryAdapter(Class<?> adapter) {
		return null;
	}

	public void clearCache() {
		if (parent != null)
			parent.clearCache();
	}

}
