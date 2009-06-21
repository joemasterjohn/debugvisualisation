package hu.cubussapiens.zestlayouts.creators;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;

/**
 * Creator for HorizontalLayoutAlgorithm
 * 
 */
public class HorizontalLayoutCreator implements ILayoutAlgorithmCreator {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		return new HorizontalLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
