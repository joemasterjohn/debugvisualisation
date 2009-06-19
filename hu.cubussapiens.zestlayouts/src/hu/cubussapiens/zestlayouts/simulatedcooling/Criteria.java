/**
 * 
 */
package hu.cubussapiens.zestlayouts.simulatedcooling;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * A criteria generates a double value from a graph configuration.
 * The lower the value, the better the graph by this criteria.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface Criteria {

	/**
	 * Apply the criteria with the given graph configuration.
	 * 
	 * The graph bounds are given by four values: (x,y,w,h). This gives the position and
	 * size of the place inside where the graph elements are visible.   
	 * 
	 * @param entities nodes in the graph
	 * @param relationships edges in the graph
	 * @param x X coordinate of the upper-left corner
	 * @param y Y coordinate of the upper-left corner
	 * @param w Width
	 * @param h Height
	 * @return
	 */
	public double apply(
			LayoutEntity[] entities,LayoutRelationship[] relationships, 
			double x, double y, double w, double h);
	
}
