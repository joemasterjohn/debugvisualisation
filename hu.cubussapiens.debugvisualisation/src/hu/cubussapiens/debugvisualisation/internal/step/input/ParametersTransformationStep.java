/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.ValueUtils;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * This transformation step filters all constant child nodes from the graph, and
 * provides state query method to list these parameters separately.
 */
public class ParametersTransformationStep extends
		AbstractGraphTransformationStep {

	/**
	 * This object should be given to the getState method in order to list the
	 * constant parameters of the given node. With this state-domain, the method
	 * will return a Collection of IVariable-s.
	 */
	public static final Object hasParameters = new Integer(-2);

	/**
	 * @param parent
	 */
	public ParametersTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		// No commands
		return false;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToGetNodeState(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected Object tryToGetNodeState(Object node, Object statedomain) {
		if (hasParameters.equals(statedomain)) {
			Collection<IVariable> vars = new ArrayList<IVariable>();
			for (Object o : getParent().getChilds(node))
				if (o instanceof IValue)
					if (ValueUtils.getID((IValue) o) == -1) {
						vars
								.add((IVariable) getEdge(node, o).iterator()
										.next());
				}

			return vars;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		List<Object> result = new ArrayList<Object>();
		for (Object o : getParent().getChilds(node))
			if (o instanceof IValue) {
				if (ValueUtils.getID((IValue) o) != -1) {
					result.add(o);
				}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getEdge(java.lang.Object, java.lang.Object)
	 */
	public Collection<Object> getEdge(Object nodea, Object nodeb) {
		return getParent().getEdge(nodea, nodeb);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		return getParent().getRoots();
	}

}
