package hu.cubussapiens.zestlayouts.factories;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmFactory;
import hu.cubussapiens.zestlayouts.simulatedcooling.ICriteria;
import hu.cubussapiens.zestlayouts.simulatedcooling.SimulatedCooling;
import hu.cubussapiens.zestlayouts.simulatedcooling.criteria.ArcAngleDistribution;
import hu.cubussapiens.zestlayouts.simulatedcooling.criteria.DistinctNodes;
import hu.cubussapiens.zestlayouts.simulatedcooling.criteria.EdgeIntersection;
import hu.cubussapiens.zestlayouts.simulatedcooling.criteria.EdgeLength;
import hu.cubussapiens.zestlayouts.simulatedcooling.criteria.InBounds;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;

/**
 * Creator for SimulatedCooling algorithm. Criteria parameters are set by
 * experiments.
 * 
 */
public class SimulatedCoolingFactory implements ILayoutAlgorithmFactory {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		return new SimulatedCooling(LayoutStyles.NO_LAYOUT_NODE_RESIZING,
				new ICriteria[] {new InBounds(), /*new PointDistribution(10),*/
				new ArcAngleDistribution(1000), new EdgeLength(0.0001),
				new EdgeIntersection(1000), new DistinctNodes(1000000)}, 15);
		// note: the values above are results of experiments, not calculations,
		// and can be refined as needed.
	}

}
