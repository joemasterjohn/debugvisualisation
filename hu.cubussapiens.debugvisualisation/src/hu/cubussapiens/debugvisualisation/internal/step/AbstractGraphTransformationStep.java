package hu.cubussapiens.debugvisualisation.internal.step;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract, partial implementation of a transformation step. This class
 * implements the common features like handling listeners and delegating
 * commands.
 */
public abstract class AbstractGraphTransformationStep implements
		IGraphTransformationStep {

	private final IRootedGraphContentProvider parent;

	/**
	 * 
	 * @param parent
	 */
	public AbstractGraphTransformationStep(IRootedGraphContentProvider parent) {
		this.parent = parent;
	}

	private final Set<IGraphChangeListener> listeners = new HashSet<IGraphChangeListener>();

	public void addListener(IGraphChangeListener listener) {
		listeners.add(listener);
	}

	public void execute(IGraphCommand command) {
		// try to execute
		boolean success = tryToExecute(command);
		// if failed, delegate to parent (if it can accept it)
		if (!success && (getParent() instanceof IGraphTransformationStep)) {
			((IGraphTransformationStep) getParent()).execute(command);
		}

	}

	/**
	 * try to execute the given command. If the command is recognized by this
	 * step, it will execute it, and return true. Otherwise the method will
	 * return false
	 * 
	 * @param command
	 * @return true only if the given command is recognized and executed by this
	 *         step. False otherwise
	 */
	protected abstract boolean tryToExecute(IGraphCommand command);

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

}
