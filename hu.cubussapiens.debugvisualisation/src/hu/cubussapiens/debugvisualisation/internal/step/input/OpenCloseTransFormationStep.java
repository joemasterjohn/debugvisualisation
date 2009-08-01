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
public class OpenCloseTransFormationStep extends
		AbstractGraphTransformationStep {

	/**
	 * @param parent
	 */
	public OpenCloseTransFormationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	final Set<Object> open = new HashSet<Object>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		if (command instanceof ToggleOpenNodeCommand) {
			System.out.println("Toggle open action got");// TODO: println
			for (Object node : command.getTarget()) {
				if (open.contains(node))
					open.remove(node);
				else
					open.add(node);
			}
			System.out.println("Open nodes: " + open.size()); // TODO: println
			trigger(new ExecutedGraphCommandEvent(command));
			return true;
		} else
		return false;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		boolean o = (StackFrameRootedGraphContentProvider.root.equals(node))
				|| (open.contains(node));
		System.out.println("Is open (" + node + "): " + o); // TODO: println
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
