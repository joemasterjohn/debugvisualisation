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
import java.util.Set;

/**
 *
 */
public class OpenCloseTransformationStep extends
		AbstractGraphTransformationStep {

	/**
	 * @param parent
	 */
	public OpenCloseTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	final Set<Object> open = new HashSet<Object>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		if (command instanceof ToggleOpenNodeCommand) {
			for (Object node : command.getTarget()) {
				if (open.contains(node))
					open.remove(node);
				else
					open.add(node);
			}
			trigger(new ExecutedGraphCommandEvent(command));
			return true;
		} else
		return false;
	}

	@Override
	protected Object tryToGetNodeState(Object node, Object statedomain) {
		if (OpenCloseNodeState.class.equals(statedomain)) {
			if (StackFrameRootedGraphContentProvider.root.equals(node))
				return OpenCloseNodeState.Root;
			if (getParent().getChilds(node).isEmpty())
				return OpenCloseNodeState.ChildLess;
			if (open.contains(node))
				return OpenCloseNodeState.Open;
			return OpenCloseNodeState.Close;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		boolean o = (StackFrameRootedGraphContentProvider.root.equals(node))
				|| (open.contains(node));
		return o ? getParent().getChilds(node) : new ArrayList<Object>();
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
