/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.api.IDigInNodes;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public class DigInAction extends GraphAction {

	/**
	 * @param viewer
	 */
	public DigInAction(GraphViewer viewer) {
		super(viewer);
		setText("Dig in");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		IDigInNodes dig = (IDigInNodes) getInput()
				.getAdapter(IDigInNodes.class);
		dig.digIn(getSelection().toList());
	}

}
