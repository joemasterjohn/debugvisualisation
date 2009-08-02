/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand;
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

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		if (command instanceof ClearCache) {
			// TODO: context sensitive cache clearing - delete cache only on
			// given command target
			childs.clear();
			edges.clear();
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
		// no provided states
		return null;
	}

	final Map<Object, Collection<Object>> childs = new HashMap<Object, Collection<Object>>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		if (!childs.containsKey(node)) {
			childs.put(node, getParent().getChilds(node));
		}
		return childs.get(node);
	}

	final Map<Object, Map<Object, Collection<Object>>> edges = new HashMap<Object, Map<Object, Collection<Object>>>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getEdge(java.lang.Object, java.lang.Object)
	 */
	public Collection<Object> getEdge(Object nodea, Object nodeb) {
		Map<Object, Collection<Object>> es;
		if (!edges.containsKey(nodea)) {
			es = new HashMap<Object, Collection<Object>>();
			edges.put(nodea, es);
		} else
			es = edges.get(nodea);

		if (!es.containsKey(nodeb)) {
			es.put(nodeb, getParent().getEdge(nodea, nodeb));
		}

		return es.get(nodeb);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		// There is no need to cache this
		return getParent().getRoots();
	}

}
