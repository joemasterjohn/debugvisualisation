package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.layouts.LayoutRegistry.LayoutEntry;

import org.eclipse.jface.action.Action;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;

/**
 * An action for selecting a layout
 */
public class SelectLayoutAction extends Action {

	private LayoutEntry layout;

	private GraphViewer viewer;

	private SelectLayoutGroup group;

	/**
	 * Initializes a select layout action
	 * 
	 * @param layout
	 *            the layout to set using this action
	 * @param viewer
	 *            the viewer to call when the action is executed
	 * @param group
	 *            the action group to notify of changes
	 */
	public SelectLayoutAction(LayoutEntry layout, GraphViewer viewer,
			SelectLayoutGroup group) {
		super(layout.getName(), AS_RADIO_BUTTON);
		this.layout = layout;
		this.group = group;
		this.viewer = viewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (viewer != null) {
			LayoutAlgorithm algorithm = layout.getLayoutCreator().create();
			viewer.setLayoutAlgorithm(algorithm, true);
		}
		group.selectLayout(this);
		super.run();

	}

}
