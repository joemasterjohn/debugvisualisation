/**
 * 
 */
package hu.gbalage.debugvisualisation.simulatedcooling;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * @author Grill Balazs (balage.g@gmail.com)
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
			if (e.getXInLayout() < x) result += 1000000;
			if (e.getXInLayout()+e.getWidthInLayout() > w) result += 1000000;
			if (e.getYInLayout() < y) result += 1000000;
			if (e.getYInLayout()+e.getHeightInLayout() > h) result += 1000000;
		}
		
		return result;
	}

}
