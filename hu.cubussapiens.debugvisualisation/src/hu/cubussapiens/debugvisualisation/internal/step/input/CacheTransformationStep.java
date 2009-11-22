/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

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
	 */
	public CacheTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
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
	public Collection<Object> getRoots() {
		// There is no need to cache this
		return getParent().getRoots();
	}

	private final Map<Object, Object> edgetargets = new HashMap<Object, Object>();

	public Object getEdgeTarget(Object edge) {
		if (!edgetargets.containsKey(edge)) {
			edgetargets.put(edge, getParent().getEdgeTarget(edge));
		}
		return edgetargets.get(edge);
	}

	private final Map<Object, Collection<Object>> childedges = new HashMap<Object, Collection<Object>>();

	public Collection<Object> getEdges(Object node) {
		if (!childedges.containsKey(node)) {
			childedges.put(node, getParent().getEdges(node));
		}
		return childedges.get(node);
	}

}
