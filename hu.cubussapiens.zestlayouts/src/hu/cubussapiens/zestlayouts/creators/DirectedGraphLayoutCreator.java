/**
 * 
 */
package hu.cubussapiens.zestlayouts.creators;

import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;

/**
 * Creator for DirectedGraphLayout. The LayoutAlgorithm created by this object is a composite
 * algorithm consisting a DirectedGraphLayoutAlgorithm and a HorizontalShift.
 *
 */
public class DirectedGraphLayoutCreator implements ILayoutAlgorithmCreator {

	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator#create()
	 */
	public LayoutAlgorithm create() {
		//return new DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		return new
		CompositeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING, new
				LayoutAlgorithm[] { new
				DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), new
				HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) });
	}

}
