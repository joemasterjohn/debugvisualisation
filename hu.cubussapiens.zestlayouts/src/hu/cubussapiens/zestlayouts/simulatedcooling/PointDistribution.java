/**
 * 
 */
package hu.cubussapiens.zestlayouts.simulatedcooling;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * This criteria punishes a graph layout if the nodes are too close to each other. Every node
 * pair is punished by a value of factor/d, where d is the distance between the nodes.
 *
 */
public class PointDistribution implements Criteria {

	private final double factor;
	
	/**
	 * Creates a criteria which punishes nodes which are too close to each other.
	 * @param factor a linear factor to determine punishment, which is factor/d, where d is the distance between the nodes.
	 */
	public PointDistribution(double factor){
		this.factor = factor;
	}
	
	/* (non-Javadoc)
	 * @see hu.cubussapiens.zestlayouts.simulatedcooling.Criteria#apply(org.eclipse.zest.layouts.LayoutEntity[], org.eclipse.zest.layouts.LayoutRelationship[], double, double, double, double)
	 */
	public double apply(LayoutEntity[] entities,
			LayoutRelationship[] relationships, double x, double y, double w,
			double h) {
		
		double result = 0;
		
		for(LayoutEntity i : entities)
			for(LayoutEntity j : entities)
				if (!i.equals(j)){
					double d = Math.pow(i.getXInLayout()-j.getXInLayout(),2) +
						Math.pow(i.getYInLayout()-j.getYInLayout(),2);
					
					if (d == 0) d=0.00001;
					
					result += factor/d;
			}
		
		return result;
	}

}
