package hu.cubussapiens.zestlayouts.creators;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

/**
 * Creator for layout algorithm, which simulates springs at the edges
 * 
 */
public class SpringLayoutCreator implements ILayoutAlgorithmCreator {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		return new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
