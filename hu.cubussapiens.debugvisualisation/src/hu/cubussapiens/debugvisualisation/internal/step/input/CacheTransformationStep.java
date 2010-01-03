/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.model.ViewModelFactory;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CacheTransformationStep extends AbstractGraphTransformationStep {

	/**
	 * @param parent
	 * @param factory
	 */
	public CacheTransformationStep(IRootedGraphContentProvider parent,
			ViewModelFactory factory) {
		super(parent, factory);
	}

	@Override
	public void clearCache() {
		// TODO: context sensitive cache clearing
		childedges.clear();
		edgetargets.clear();
		super.clearCache();
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		// There is no need to cache this
		return getParent().getRoots();
	}

	private final Map<IDVVariable, IDVValue> edgetargets = new HashMap<IDVVariable, IDVValue>();

	public IDVValue getEdgeTarget(IDVVariable edge) {
		if (!edgetargets.containsKey(edge)) {
			edgetargets.put(edge, getParent().getEdgeTarget(edge));
		}
		return edgetargets.get(edge);
	}

	private final Map<IDVValue, Collection<IDVVariable>> childedges = new HashMap<IDVValue, Collection<IDVVariable>>();

	public Collection<IDVVariable> getEdges(IDVValue node) {
		if (!childedges.containsKey(node)) {
			childedges.put(node, getParent().getEdges(node));
		}
		return childedges.get(node);
	}

}
