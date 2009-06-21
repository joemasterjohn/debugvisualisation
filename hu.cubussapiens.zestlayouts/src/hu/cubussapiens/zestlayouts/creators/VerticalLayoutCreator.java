package hu.cubussapiens.zestlayouts.creators;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;

/**
 * Creator for VerticalLayoutAlgorithm
 * 
 */
public class VerticalLayoutCreator implements ILayoutAlgorithmCreator {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		return new VerticalLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
