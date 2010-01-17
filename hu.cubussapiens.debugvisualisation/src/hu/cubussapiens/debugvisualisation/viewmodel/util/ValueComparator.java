package hu.cubussapiens.debugvisualisation.viewmodel.util;

import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;

import java.util.Comparator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutEntity;

public class ValueComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		Object element1 = ((GraphNode) ((LayoutEntity) o1).getGraphData())
				.getData();
		Object element2 = ((GraphNode) ((LayoutEntity) o2).getGraphData())
				.getData();
		if (element1 instanceof IDVValue && element2 instanceof IDVValue) {
			IDVValue value1 = (IDVValue) element1, value2 = (IDVValue) element2;
			try {
				IDVVariable parent1 = value1.getParent();
				IDVVariable parent2 = value2.getParent();
				if (parent1 != null && parent2 != null) {
					String name1 = parent1.getRelatedVariable().getName();
					String name2 = parent2.getRelatedVariable().getName();
					return name1.compareTo(name2);
				}
			} catch (DebugException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

}
