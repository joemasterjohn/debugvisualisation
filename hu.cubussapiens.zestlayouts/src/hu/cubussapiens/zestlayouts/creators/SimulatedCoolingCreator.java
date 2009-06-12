/**
 * 
 */
package hu.cubussapiens.zestlayouts.creators;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;
import hu.cubussapiens.zestlayouts.simulatedcooling.Criteria;
import hu.cubussapiens.zestlayouts.simulatedcooling.EdgeIntersection;
import hu.cubussapiens.zestlayouts.simulatedcooling.EdgeLength;
import hu.cubussapiens.zestlayouts.simulatedcooling.InBounds;
import hu.cubussapiens.zestlayouts.simulatedcooling.PointDistribution;
import hu.cubussapiens.zestlayouts.simulatedcooling.SimulatedCooling;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class SimulatedCoolingCreator implements ILayoutAlgorithmCreator {

	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator#create()
	 */
	public LayoutAlgorithm create() {
		return new SimulatedCooling(LayoutStyles.NO_LAYOUT_NODE_RESIZING,new Criteria[]{
			new InBounds(),
			new PointDistribution(1000),
			new EdgeLength(0.001)
			,new EdgeIntersection(100)
		});
	}

}
