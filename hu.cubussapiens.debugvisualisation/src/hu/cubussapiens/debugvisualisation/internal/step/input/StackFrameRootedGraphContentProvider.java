/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * This class provides the graph content based on a stack frame
 */
public class StackFrameRootedGraphContentProvider implements
		IRootedGraphContentProvider {

	/**
	 * 
	 */
	public static final Object root = new Integer(-1);

	final IStackFrame sf;

	/**
	 * @param sf
	 * 
	 */
	public StackFrameRootedGraphContentProvider(IStackFrame sf) {
		this.sf = sf;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		List<Object> os = new ArrayList<Object>();
		if (root.equals(node)) {
			try {
				for (IVariable v : sf.getVariables()) {
					os.add(v.getValue());
				}
			} catch (DebugException e) {
				Activator.getDefault().logError(e,
						"Can't list context variables");
			}
		}
		if (node instanceof IValue) {
			IValue value = (IValue) node;
			try {
				for (IVariable v : value.getVariables()) {
					os.add(v.getValue());
				}
			} catch (DebugException e) {
				Activator.getDefault().logError(e, "Can't list variables");
			}
		}
		return os;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getEdge(java.lang.Object, java.lang.Object)
	 */
	public Collection<Object> getEdge(Object nodea, Object nodeb) {
		List<Object> os = new ArrayList<Object>();
		if (root.equals(nodea)) {
			try {
				for (IVariable v : sf.getVariables()) {
					if (v.getValue().equals(nodeb))
						os.add(v);
				}
			} catch (DebugException e) {
				Activator.getDefault().logError(e,
						"Can't list context variables");
			}
		}
		if (nodea instanceof IValue) {
			IValue value = (IValue) nodea;
			try {
				for (IVariable v : value.getVariables()) {
					if (v.getValue().equals(nodeb))
						os.add(v);
				}
			} catch (DebugException e) {
				Activator.getDefault().logError(e, "Can't list variables");
			}
		}
		return os;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		List<Object> roots = new ArrayList<Object>();
		roots.add(root);
		return roots;
	}

}
