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
 * Creator for SimulatedCooling algorithm. Criteria parameters are set by experiments.
 *
 */
public class SimulatedCoolingCreator implements ILayoutAlgorithmCreator {

	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator#create()
	 */
	public LayoutAlgorithm create() {
		return new SimulatedCooling(LayoutStyles.NO_LAYOUT_NODE_RESIZING,new Criteria[]{
			new InBounds(), //we want the nodes to be placed inside the graph bounds
			new PointDistribution(1000), //we want the nodes to be far from each other
			new EdgeLength(0.001) //we want the edges to be as short as possible
			,new EdgeIntersection(100) //we don't want edges to intersect each other
		});
		//note: the values above are results of experiments, not calculations, 
		//and can be refined as needed.
	}

}
