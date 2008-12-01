/**
 * 
 */
package hu.gbalage.debugvisualisation.simulatedcooling;

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
	 * @param entities
	 * @param relationships
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public double apply(
			LayoutEntity[] entities,LayoutRelationship[] relationships, 
			double x, double y, double w, double h);
	
}
