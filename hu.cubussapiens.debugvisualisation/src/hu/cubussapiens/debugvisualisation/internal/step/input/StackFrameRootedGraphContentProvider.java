/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

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
	final ViewModelFactory factory;

	/**
	 * @param sf
	 * @param factory
	 * 
	 */
	public StackFrameRootedGraphContentProvider(IStackFrame sf,
			ViewModelFactory factory) {
		this.sf = sf;
		this.factory = factory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider
	 * #getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		List<IDVValue> roots = new ArrayList<IDVValue>();
		try {
			for (IVariable v : sf.getVariables()) {
				roots.add(factory.getValue(v.getValue(), this, v));
			}
		} catch (DebugException e) {
			DebugVisualisationPlugin.getDefault().logError(e,
					"Can't retrieve root values");
		}
		return roots;
	}

	public IDVValue getEdgeTarget(IDVVariable e) {
		IVariable edge = (IVariable) e.getAdapter(IVariable.class);
		try {
			return factory.getValue(edge.getValue(), this, e);
		} catch (DebugException e1) {
			DebugVisualisationPlugin.getDefault().logError(e1,
					"Can't retrieve value of " + edge);
		}
		return null;
	}

	public Collection<IDVVariable> getEdges(IDVValue n) {
		List<IDVVariable> os = new ArrayList<IDVVariable>();
		IValue node = (IValue) n.getAdapter(IValue.class);
		try {
			for (IVariable v : node.getVariables()) {
				os.add(factory.getVariable(v, this, n));
			}
		} catch (DebugException e) {
			DebugVisualisationPlugin.getDefault().logError(e,
					"Can't list variables");
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
