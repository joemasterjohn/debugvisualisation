/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.filtering.IVariableFilter;
import hu.cubussapiens.debugvisualisation.filtering.IVariableFilterProvider;
import hu.cubussapiens.debugvisualisation.filtering.internal.VariableFilterProvider;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand;
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
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		// no commands
		return false;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToGetNodeState(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected Object tryToGetNodeState(Object node, Object statedomain) {
		// no stored states
		return null;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		try {
			IVariableFilter vf = (node instanceof IValue) ? provider
					.getFilter(((IValue) node).getReferenceTypeName()) : null;
			if (vf == null)
				return getParent().getChilds(node);
			List<Object> os = new ArrayList<Object>();
			for (IVariable v : vf.filter((IValue) node)) {
				os.add(v.getValue());
			}
			return os;
		} catch (DebugException e) {
			Activator.getDefault()
					.logError(e, "Can't apply filter for " + node);
			return getParent().getChilds(node);
		}
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getEdge(java.lang.Object, java.lang.Object)
	 */
	public Collection<Object> getEdge(Object nodea, Object nodeb) {
		try {
			IVariableFilter vf = (nodea instanceof IValue) ? provider
					.getFilter(((IValue) nodea).getReferenceTypeName()) : null;
			if (vf == null)
				return getParent().getEdge(nodea, nodeb);
			List<Object> os = new ArrayList<Object>();
			for (IVariable v : vf.filter((IValue) nodea))
				if (nodeb.equals(v.getValue())) {
					os.add(v);
				}
			return os;
		} catch (DebugException e) {
			Activator.getDefault().logError(e,
					"Can't apply filter for " + nodea);
			return getParent().getChilds(nodea);
		}
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		return getParent().getRoots();
	}

}
