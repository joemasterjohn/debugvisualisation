package hu.cubussapiens.debugvisualisation.internal.input;

import hu.cubussapiens.debugvisualisation.Activator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

/**
 * A debug context input that maintains its visible and open nodes.
 */
public class DebugContextInputWithNodeVisibility extends DebugContextInput {

	/**
	 * Visible nodes
	 */
	protected final Set<Integer> visible = new HashSet<Integer>();

	/**
	 * Open nodes
	 */
	protected final Set<Integer> open = new HashSet<Integer>();

	/**
	 * Contains nodes that are hidden by the user and should not be shown again
	 * (until the user says so)
	 */
	protected final Set<Integer> hidden = new HashSet<Integer>();

	protected void setVisibility(Integer node, boolean visibility) {
		if (visibility)
			visible.add(node);
		else
			visible.remove(node);
	}

	/**
	 * @param sf
	 * @throws DebugException
	 */
	public DebugContextInputWithNodeVisibility(IStackFrame sf)
			throws DebugException {
		super(sf);
		setVisibility(root, true);
		openNode(root);
	}

	@Override
	protected void onRefresh() {
		// setVisibility(root, true);
		/*
		 * try{ openNode(root); }catch(Exception e){ e.printStackTrace(); }
		 */
		// if (visible.contains(root)) openNode(root);
		if (open != null) {
			// System.out.println("onRefresh.. "+open.size());
			openNode(root);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Integer> getVisibleNodes() {
		return visible;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOpen(Integer node) {
		if (node == -1)
			return true;
		if (open == null)
			return false;
		return open.contains(node);
	}

	/**
	 * {@inheritDoc}
	 */
	public void toggleOpen(Integer node) {
		if (isOpen(node)) {
			closeNode(node);
			setVisibility(node, true); // reopen the closed node if it is closed
										// when a cycle is present in the
										// context
		} else {
			openNode(node);
		}
		trigger().openStateChanged(node, isOpen(node));
	}

	private void openNode(Integer node) {
		// open closed node
		open.add(node);
		// reload child variables
		try {
			refreshNode(node);
		} catch (DebugException e) {
			Activator.getDefault().logError(e, "Can't refresh node " + node);
		}
		// show all child nodes
		for (Integer child : getChilds(node)) {
			// if not hidden
			if (!hidden.contains(child)) {
				setVisibility(child, true);
			}

		}
	}

	private void closeNode(Integer node) {
		if (node.equals(root))
			return; // root node can't be closed
		open.remove(node);
		for (Integer child : getChilds(node)) {
			if (isOpen(child))
				closeNode(child);
			setVisibility(child, false);
		}
	}

	public void hideNode(Integer node) {
		setVisibility(node, false);
		hidden.add(node);
		trigger().visibilityChanged(node, false);
	}

	public void showHiddenChildNodes(Integer node) {
		for (Integer child : getChilds(node)) {
			if (hidden.contains(child)) {
				hidden.remove(child);
				if (isOpen(node)) {
					setVisibility(child, true);
				}
			}
		}
		trigger().visibilityChanged(node, true);
	}

}
