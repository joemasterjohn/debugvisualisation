package hu.cubussapiens.zestlayouts.creators;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;

/**
 * Creator for RadialLayoutAlgorithm
 * 
 */
public class RadialLayoutCreator implements ILayoutAlgorithmCreator {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		return new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
