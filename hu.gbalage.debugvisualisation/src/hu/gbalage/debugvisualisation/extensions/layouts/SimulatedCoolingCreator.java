/**
 * 
 */
package hu.gbalage.debugvisualisation.extensions.layouts;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;

import hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator;
import hu.gbalage.debugvisualisation.simulatedcooling.Criteria;
import hu.gbalage.debugvisualisation.simulatedcooling.EdgeLength;
import hu.gbalage.debugvisualisation.simulatedcooling.InBounds;
import hu.gbalage.debugvisualisation.simulatedcooling.PointDistribution;
import hu.gbalage.debugvisualisation.simulatedcooling.SimulatedCooling;

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
		});
	}

}
