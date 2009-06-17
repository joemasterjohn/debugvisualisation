/**
 * 
 */
package hu.cubussapiens.zestlayouts.simulatedcooling;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * This criteria punishes out-bounded nodes with a large value. A node is out-bound if
 * its position is not in the bounds of the graph.
 *
 */
public class InBounds implements Criteria {

	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.simulatedcooling.Criteria#apply(org.eclipse.zest.layouts.LayoutEntity[], org.eclipse.zest.layouts.LayoutRelationship[], double, double, double, double)
	 */
	public double apply(LayoutEntity[] entities,
			LayoutRelationship[] relationships, double x, double y, double w,
			double h) {
		
		double result = 0;
		
		for (LayoutEntity e : entities){
			double xe = e.getXInLayout();
			double ye = e.getYInLayout();
			double we = e.getWidthInLayout();
			double he = e.getHeightInLayout();
			if (xe < x) result += 1000000+100*(x-xe);
			if (xe+we > w) result += 1000000 + 100*(xe+we-w);
			if (ye < y) result += 1000000 + 100*(y-ye);
			if (ye+he > h) result += 1000000 + 100*(ye+he-h);
		}
		
		return result;
	}

}
