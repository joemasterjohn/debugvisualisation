/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.ExecutedGraphCommandEvent;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class HideNodesTransformationStep extends
		AbstractGraphTransformationStep {

	private final Set<Object> hidden = new HashSet<Object>();

	/**
	 * @param parent
	 */
	public HideNodesTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		if (command instanceof HideNodesCommand) {
			for (Object o : command.getTarget())
				hidden.add(o);
			trigger(new ExecutedGraphCommandEvent(command));
			return true;
		}
		if (command instanceof ShowHiddenChildNodesCommand) {
			for (Object o : command.getTarget())
				for (Object u : getParent().getChilds(o))
					if (hidden.contains(u))
						hidden.remove(u);
			trigger(new ExecutedGraphCommandEvent(command));
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToGetNodeState(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected Object tryToGetNodeState(Object node, Object statedomain) {
		// no provided node states
		return null;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		List<Object> result = new ArrayList<Object>();
		for (Object o : getParent().getChilds(node))
			if (!hidden.contains(o)) 
					result.add(o);
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
		// roots can't be hidden
		return getParent().getRoots();
	}

}
