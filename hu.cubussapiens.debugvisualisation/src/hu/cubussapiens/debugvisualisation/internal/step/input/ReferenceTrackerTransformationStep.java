/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.api.IReferenceTracker;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
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

	public Collection<IDVVariable> getReferences(IDVValue node) {
		if (refs.containsKey(node))
			return refs.get(node);
		return new HashSet<IDVVariable>();
	}

	final Map<IDVValue, Set<IDVVariable>> refs = new HashMap<IDVValue, Set<IDVVariable>>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		return getParent().getRoots();
	}

	public IDVValue getEdgeTarget(IDVVariable edge) {
		IDVValue n = getParent().getEdgeTarget(edge);
		if (!refs.containsKey(n))
			refs.put(n, new HashSet<IDVVariable>());
		refs.get(n).add(edge);
		return n;
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		return getParent().getEdges(node);
	}

}
