package hu.cubussapiens.zestlayouts.factories;

import hu.cubussapiens.debugvisualisation.layouts.ILayoutAlgorithmFactory;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ValueComparator;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;

/**
 * Creator for HorizontalTreeLayoutAlgorithm
 * 
 */
public class HorizontalTreeLayoutFactory implements ILayoutAlgorithmFactory {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		HorizontalTreeLayoutAlgorithm algorithm = new HorizontalTreeLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		algorithm.setComparator(new ValueComparator());
		return algorithm;
	}

}
