package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.api.IHiddenNodes;
import hu.cubussapiens.debugvisualisation.internal.api.IOpenCloseNodes;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;

import java.util.ArrayList;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Action which opens or closes the selected nodes on a graph.
 * 
 */
public class ToggleOpenAction extends GraphAction {

	/**
	 * Creates a ToggleOpenAction
	 * 
	 * @param viewer
	 */
	public ToggleOpenAction(GraphViewer viewer) {
		super(viewer);
		setText("Open/Close");
		setToolTipText("Opens or closes selected nodes");
	}

	@Override
	public void run() {
		IOpenCloseNodes ocn = (IOpenCloseNodes) getInput().getAdapter(
				IOpenCloseNodes.class);
		IHiddenNodes hidden = (IHiddenNodes) getInput().getAdapter(
				IHiddenNodes.class);
		if (!getSelection().isEmpty()) {
			IDVValue element = (IDVValue) getSelection().getFirstElement();
			ocn.toggleOpenNode(element);
			ArrayList<IDVValue> elementList = new ArrayList<IDVValue>();
			elementList.add(element);
			hidden.showHiddenChildNodes(elementList);
		}
	}

}
