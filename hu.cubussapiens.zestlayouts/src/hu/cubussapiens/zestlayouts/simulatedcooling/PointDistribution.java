/**
 * 
 */
package hu.cubussapiens.zestlayouts.simulatedcooling;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class PointDistribution implements Criteria {

	private final double factor;
	
	public PointDistribution(double factor){
		this.factor = factor;
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.simulatedcooling.Criteria#apply(org.eclipse.zest.layouts.LayoutEntity[], org.eclipse.zest.layouts.LayoutRelationship[], double, double, double, double)
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
