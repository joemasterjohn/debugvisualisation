/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.api.IReferenceTracker;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class ReferenceTrackerTransformationStep extends
		AbstractGraphTransformationStep implements IReferenceTracker {

	/**
	 * @param parent
	 */
	public ReferenceTrackerTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (IReferenceTracker.class.equals(adapter))
			return this;
		return super.tryAdapter(adapter);
	}

	@Override
	public void clearCache() {
		refs.clear();
		super.clearCache();
	}

	public Collection<Object> getReferences(Object node) {
		if (refs.containsKey(node))
			return refs.get(node);
		return new HashSet<Object>();
	}

	final Map<Object, Set<Object>> refs = new HashMap<Object, Set<Object>>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		return getParent().getRoots();
	}

	public Object getEdgeTarget(Object edge) {
		Object n = getParent().getEdgeTarget(edge);
		if (!refs.containsKey(n))
			refs.put(n, new HashSet<Object>());
		refs.get(n).add(edge);
		return n;
	}

	public Collection<Object> getEdges(Object node) {
		return getParent().getEdges(node);
	}

}
