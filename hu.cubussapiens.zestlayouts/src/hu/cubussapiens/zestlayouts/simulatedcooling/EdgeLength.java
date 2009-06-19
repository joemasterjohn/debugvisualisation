/**
 * 
 */
package hu.cubussapiens.zestlayouts.simulatedcooling;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * This criteria punishes long edges by multiplying edge length by the given values.
 * The value of punishment is linear to the edge length.
 */
public class EdgeLength implements Criteria {

	private final double factor;
	
	/**
	 * Creates a criteria which punishes long edges
	 * @param factor a linear factor to determine punishment, which is factor*length, where
	 * length is the length of the edge
	 */
	public EdgeLength(double factor){
		this.factor = factor;
	}
	
	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.simulatedcooling.Criteria#apply(org.eclipse.zest.layouts.LayoutEntity[], org.eclipse.zest.layouts.LayoutRelationship[], double, double, double, double)
	 */
	public double apply(LayoutEntity[] entities,
			LayoutRelationship[] relationships, double x, double y, double w,
			double h) {
		
		double result = 0;
		
		for(LayoutRelationship r : relationships){
			LayoutEntity i = r.getSourceInLayout();
			LayoutEntity j = r.getDestinationInLayout();
			
			double d = Math.pow(i.getXInLayout()-j.getXInLayout(),2) +
				Math.pow(i.getYInLayout()-j.getYInLayout(),2);
			
			result += Math.sqrt(d)*factor;
		}
		
		return result;
	}

}
