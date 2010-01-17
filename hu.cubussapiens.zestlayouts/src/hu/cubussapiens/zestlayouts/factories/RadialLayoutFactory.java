package hu.cubussapiens.zestlayouts.factories;

import hu.cubussapiens.debugvisualisation.layouts.ILayoutAlgorithmFactory;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ValueComparator;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;

/**
 * Creator for RadialLayoutAlgorithm
 * 
 */
public class RadialLayoutFactory implements ILayoutAlgorithmFactory {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		RadialLayoutAlgorithm algorithm = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		algorithm.setComparator(new ValueComparator());
		return algorithm;
	}

}
