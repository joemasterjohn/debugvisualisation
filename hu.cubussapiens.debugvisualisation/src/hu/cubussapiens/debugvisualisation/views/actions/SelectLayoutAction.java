package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.zestlayouts.LayoutRegistry.LayoutEntry;

import java.util.Comparator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.action.Action;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutEntity;

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
			algorithm.setComparator(new ValueComparator());
			viewer.setLayoutAlgorithm(algorithm, true);
		}
		group.selectLayout(this);
		super.run();

	}

	private class ValueComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			Object element1 = ((GraphNode) ((LayoutEntity) o1).getGraphData())
					.getData();
			Object element2 = ((GraphNode) ((LayoutEntity) o2).getGraphData())
					.getData();
			if (element1 instanceof IDVValue && element2 instanceof IDVValue) {
				IDVValue value1 = (IDVValue) element1, value2 = (IDVValue) element2;
				try {
					String name1 = value1.getParent().getRelatedVariable()
							.getName();
					String name2 = value2.getParent().getRelatedVariable()
							.getName();
					return name1.compareTo(name2);
				} catch (DebugException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return 0;
		}

	}

}
