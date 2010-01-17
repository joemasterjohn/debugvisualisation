package hu.cubussapiens.zestlayouts.factories;

import hu.cubussapiens.debugvisualisation.layouts.ILayoutAlgorithmFactory;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ValueComparator;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

/**
 * A layout algorithm to position nodes as a tree
 * 
 */
public class TreeLayoutFactory implements ILayoutAlgorithmFactory {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		TreeLayoutAlgorithm algorithm = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		algorithm.setComparator(new ValueComparator());
		return algorithm;
	}

}
