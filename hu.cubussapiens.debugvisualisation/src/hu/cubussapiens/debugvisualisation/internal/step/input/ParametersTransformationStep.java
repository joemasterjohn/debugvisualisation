/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.ValueUtils;
import hu.cubussapiens.debugvisualisation.internal.api.INodeParameters;
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
	 */
	public ParametersTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	public Collection<Object> getParameters(Object node) {
		Collection<Object> vars = new ArrayList<Object>();
		for (Object u : getParent().getEdges(node)) {
			Object o = getParent().getEdgeTarget(u);
			if (o instanceof IValue)
				if (ValueUtils.getID((IValue) o) == -1) {
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
	public Collection<Object> getRoots() {
		return getParent().getRoots();
	}

	public Object getEdgeTarget(Object edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<Object> getEdges(Object node) {
		List<Object> result = new ArrayList<Object>();
		for (Object u : getParent().getEdges(node)) {
			Object o = getParent().getEdgeTarget(u);
			if (o instanceof IValue) {
				if (ValueUtils.getID((IValue) o) != -1) {
					result.add(u);
				}
			}
		}
		return result;
	}

}
