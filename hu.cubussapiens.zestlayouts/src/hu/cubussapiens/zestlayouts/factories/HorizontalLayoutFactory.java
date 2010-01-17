package hu.cubussapiens.zestlayouts.factories;

import hu.cubussapiens.debugvisualisation.layouts.ILayoutAlgorithmFactory;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ValueComparator;

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
		HorizontalLayoutAlgorithm algorithm = new HorizontalLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		algorithm.setComparator(new ValueComparator());
		return algorithm;
	}

}
