/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.zestlayouts.LayoutRegistry.LayoutEntry;

import org.eclipse.jface.action.Action;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * An action for selecting a layout
 */
public class SelectLayoutAction extends Action {

	private LayoutEntry layout;
	private GraphViewer viewer;
	private SelectLayoutGroup group;
	
	public SelectLayoutAction(LayoutEntry layout, GraphViewer viewer, SelectLayoutGroup group) {
		super(layout.getName(),AS_RADIO_BUTTON);
		this.layout = layout;
		this.group = group;
		this.viewer = viewer;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (viewer != null)
			viewer.setLayoutAlgorithm(layout.getLayoutCreator().create(),true);
		group.selectLayout(this);
		super.run();
		
	}
	
	
}
