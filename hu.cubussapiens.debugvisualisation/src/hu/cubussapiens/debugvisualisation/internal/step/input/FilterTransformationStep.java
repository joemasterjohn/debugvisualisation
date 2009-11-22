/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.filtering.IVariableFilter;
import hu.cubussapiens.debugvisualisation.filtering.IVariableFilterProvider;
import hu.cubussapiens.debugvisualisation.filtering.internal.VariableFilterProvider;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Transformation step to execute filtering
 */
public class FilterTransformationStep extends AbstractGraphTransformationStep {

	final IVariableFilterProvider provider;

	/**
	 * @param parent
	 */
	public FilterTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
		provider = new VariableFilterProvider();
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		return getParent().getRoots();
	}

	public Object getEdgeTarget(Object edge) {
		if (edge instanceof IVariable)
			try {
				return ((IVariable) edge).getValue();
			} catch (DebugException e) {
				Activator.getDefault().logError(e,
						"Can't retrieve value of " + edge);
			}
		return null;
	}

	public Collection<Object> getEdges(Object node) {
		try {
			IVariableFilter vf = (node instanceof IValue) ? provider
					.getFilter(((IValue) node).getReferenceTypeName()) : null;
			if (vf == null)
				return getParent().getEdges(node);
			List<Object> os = new ArrayList<Object>();
			for (IVariable v : vf.filter((IValue) node)) {
				os.add(v);
			}
			return os;
		} catch (DebugException e) {
			Activator.getDefault()
					.logError(e, "Can't apply filter for " + node);
			return getParent().getEdges(node);
		}
	}

}
