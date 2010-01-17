package hu.cubussapiens.zestlayouts.factories;

import hu.cubussapiens.debugvisualisation.layouts.ILayoutAlgorithmFactory;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ValueComparator;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;

/**
 * Creator for DirectedGraphLayout. The LayoutAlgorithm created by this object
 * is a composite algorithm consisting a DirectedGraphLayoutAlgorithm and a
 * HorizontalShift.
 * 
 */
public class DirectedGraphLayoutFactory implements ILayoutAlgorithmFactory {

	/**
	 * {@inheritDoc}
	 */
	public LayoutAlgorithm create() {
		// return new
		// DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		CompositeLayoutAlgorithm algorithm = new CompositeLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[] {
						new DirectedGraphLayoutAlgorithm(
								LayoutStyles.NO_LAYOUT_NODE_RESIZING),
						new HorizontalShift(
								LayoutStyles.NO_LAYOUT_NODE_RESIZING) });
		algorithm.setComparator(new ValueComparator());
		return algorithm;
	}

}
