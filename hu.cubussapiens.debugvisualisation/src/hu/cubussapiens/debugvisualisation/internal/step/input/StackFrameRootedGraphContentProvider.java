/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.model.impl.DVValueImpl;
import hu.cubussapiens.debugvisualisation.internal.model.impl.DVVariablesImpl;
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

	final IStackFrame sf;

	/**
	 * @param sf
	 * 
	 */
	public StackFrameRootedGraphContentProvider(IStackFrame sf) {
		this.sf = sf;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		List<IDVValue> roots = new ArrayList<IDVValue>();
		try {
			for (IVariable v : sf.getVariables()) {
				roots.add(new DVValueImpl(v.getValue(), this, v));
			}
		} catch (DebugException e) {
			Activator.getDefault().logError(e, "Can't retrieve root values");
		}
		return roots;
	}

	public IDVValue getEdgeTarget(IDVVariable e) {
		IVariable edge = (IVariable) e.getAdapter(IVariable.class);
			try {
			return new DVValueImpl(edge.getValue(), this, e);
			} catch (DebugException e1) {
				Activator.getDefault().logError(e1,
						"Can't retrieve value of " + edge);
			}
		return null;
	}

	public Collection<IDVVariable> getEdges(IDVValue n) {
		List<IDVVariable> os = new ArrayList<IDVVariable>();
		IValue node = (IValue) n.getAdapter(IValue.class);
		if (node instanceof IValue) {
			IValue value = (IValue) node;
			try {
				for (IVariable v : value.getVariables()) {
					os.add(new DVVariablesImpl(v, this, n));
				}
			} catch (DebugException e) {
				Activator.getDefault().logError(e, "Can't list variables");
			}
		}
		return os;
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return null;
	}

	public void clearCache() {

	}

}
