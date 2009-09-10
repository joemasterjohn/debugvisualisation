package hu.cubussapiens.zestlayouts.factories;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmFactory;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;

/**
 * Creator for HorizontalLayoutAlgorithm
 * 
 */
public class HorizontalLayoutFactory implements ILayoutAlgorithmFactory {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		return new HorizontalLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
