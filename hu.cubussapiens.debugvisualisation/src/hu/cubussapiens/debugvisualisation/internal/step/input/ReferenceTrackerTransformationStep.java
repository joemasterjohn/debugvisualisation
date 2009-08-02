/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand;
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
		AbstractGraphTransformationStep {

	/**
	 * Give this object to the getNodeState method in order to receive
	 * references to the given node.
	 */
	public static final Object getReferences = new Integer(-3);

	/**
	 * @param parent
	 */
	public ReferenceTrackerTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		if (command instanceof ClearCache) {
			refs.clear();
			// always delegate this command in order to clear all cache in the
			// transformation chain
			return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToGetNodeState(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected Object tryToGetNodeState(Object node, Object statedomain) {
		if (getReferences.equals(statedomain)) {
			if (refs.containsKey(node))
				return refs.get(node);
			return new HashSet<Object>();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		Collection<Object> ch = getParent().getChilds(node);
		// Trigger pre-caching, we will need these data anyway
		for (Object c : ch)
			getEdge(node, c);
		return ch;
	}

	final Map<Object, Set<Object>> refs = new HashMap<Object, Set<Object>>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getEdge(java.lang.Object, java.lang.Object)
	 */
	public Collection<Object> getEdge(Object nodea, Object nodeb) {
		Collection<Object> edges = getParent().getEdge(nodea, nodeb);
		if (!refs.containsKey(nodeb)) {
			refs.put(nodeb, new HashSet<Object>());
		}
		refs.get(nodeb).addAll(edges);
		return edges;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		return getParent().getRoots();
	}

}
