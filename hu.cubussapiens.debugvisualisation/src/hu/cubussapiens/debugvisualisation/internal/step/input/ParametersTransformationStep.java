/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.ValueUtils;
import hu.cubussapiens.debugvisualisation.internal.api.INodeParameters;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.model.ViewModelFactory;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.debug.core.model.IValue;

/**
 * This transformation step filters all constant child nodes from the graph, and
 * provides state query method to list these parameters separately.
 */
public class ParametersTransformationStep extends
		AbstractGraphTransformationStep implements INodeParameters {

	/**
	 * @param parent
	 * @param factory
	 */
	public ParametersTransformationStep(IRootedGraphContentProvider parent,
			ViewModelFactory factory) {
		super(parent, factory);
	}

	public Collection<IDVVariable> getParameters(IDVValue node) {
		Collection<IDVVariable> vars = new ArrayList<IDVVariable>();
		for (IDVVariable u : getParent().getEdges(node)) {
			IValue o = (IValue) getParent().getEdgeTarget(u).getAdapter(
					IValue.class);
			if (ValueUtils.getID(o) == -1) {
				if (!vars.contains(u))
					vars.add(u);
			}
		}

		return vars;
	}

	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (INodeParameters.class.equals(adapter))
			return this;
		return super.tryAdapter(adapter);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		return getParent().getRoots();
	}

	public IDVValue getEdgeTarget(IDVVariable edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		List<IDVVariable> result = new ArrayList<IDVVariable>();
		for (IDVVariable u : getParent().getEdges(node)) {
			IValue o = (IValue) getParent().getEdgeTarget(u).getAdapter(
					IValue.class);
			if (ValueUtils.getID(o) != -1) {
				result.add(u);
			}
		}
		return result;
	}

}
